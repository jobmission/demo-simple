package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/sse")
public class SseTest {
    private static Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping(value = "/subscribe", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter push(@RequestParam(value = "id", required = false, defaultValue = "1") String id) throws IOException {
        // 超时时间设置为3s，用于演示客户端自动重连
        SseEmitter sseEmitter = new SseEmitter(1000L * 10);
        // 设置前端的重试时间为1s
        sseEmitter.send(SseEmitter.event().reconnectTime(1000 * 5).data("连接成功 " + df.format(LocalDateTime.now())));
        sseCache.put(id, sseEmitter);
        System.out.println("add " + id);
        sseEmitter.onTimeout(() -> {
            System.out.println(id + "超时");
            sseCache.remove(id);
        });
        sseEmitter.onCompletion(() -> System.out.println("完成！！！"));
        return sseEmitter;
    }

    @GetMapping(value = "/push")
    public String push(@RequestParam(value = "id", required = false, defaultValue = "1") String id, @RequestParam(value = "content", defaultValue = "content") String content) throws IOException {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            sseEmitter.send(content + " " + df.format(LocalDateTime.now()));
        }
        return "pushed";
    }

    @GetMapping(value = "/over")
    public String over(@RequestParam(value = "id", defaultValue = "1") String id) {
        SseEmitter sseEmitter = sseCache.get(id);
        if (sseEmitter != null) {
            sseEmitter.complete();
            sseCache.remove(id);
        }
        return "over";
    }
}


