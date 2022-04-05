package com.example.demo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MinioTest {

    @Disabled
    @Test
    public void testUpload() throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
            MinioClient minioClient = MinioClient.builder().endpoint("https://play.min.io").credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG").build();

            // Check if the bucket already exists.
            boolean isExist =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket("zhangsan").build());
            if (isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("zhangsan").build());
            }

            // Create some content for the object.
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                builder.append(
                    "Sphinx of black quartz, judge my vow: Used by Adobe InDesign to display font samples. ");
                builder.append("(29 letters)\n");
                builder.append(
                    "Jackdaws love my big sphinx of quartz: Similarly, used by Windows XP for some fonts. ");
                builder.append("(31 letters)\n");
                builder.append(
                    "Pack my box with five dozen liquor jugs: According to Wikipedia, this one is used on ");
                builder.append("NASAs Space Shuttle. (32 letters)\n");
                builder.append(
                    "The quick onyx goblin jumps over the lazy dwarf: Flavor text from an Unhinged Magic Card. ");
                builder.append("(39 letters)\n");
                builder.append(
                    "How razorback-jumping frogs can level six piqued gymnasts!: Not going to win any brevity ");
                builder.append("awards at 49 letters long, but old-time Mac users may recognize it.\n");
                builder.append(
                    "Cozy lummox gives smart squid who asks for job pen: A 41-letter tester sentence for Mac ");
                builder.append("computers after System 7.\n");
                builder.append(
                    "A few others we like: Amazingly few discotheques provide jukeboxes; Now fax quiz Jack! my ");
                builder.append("brave ghost pled; Watch Jeopardy!, Alex Trebeks fun TV quiz game.\n");
                builder.append("---\n");
            }

            // Create a InputStream for object upload.
            ByteArrayInputStream bais = new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));

            // Create headers
            Map<String, String> headers = new HashMap<>();
            // Add custom content type
            headers.put("Content-Type", "application/octet-stream");
            // Add storage class
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");

            // Add custom/user metadata
            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put("My-Project", "Project One");
            userMetadata.put("author", URLEncoder.encode("张三", "UTF-8"));

            // Create object 'my-objectname' with user metadata and other properties in 'my-bucketname'
            // with content
            // from the input stream.
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder().bucket("zhangsan").object("my-objectname").stream(
                        bais, bais.available(), -1)
                    .headers(headers)
                    .userMetadata(userMetadata)
                    .build());
            bais.close();

            System.out.println(objectWriteResponse.headers());
            System.out.println("my-objectname is uploaded successfully");

            // Get information of an object.
            StatObjectResponse stat =
                minioClient.statObject(
                    StatObjectArgs.builder()
                        .bucket("zhangsan")
                        .object("my-objectname")
                        .build());
            System.out.println(stat);

        } catch (MinioException e) {
            e.printStackTrace();
        }
    }
}

