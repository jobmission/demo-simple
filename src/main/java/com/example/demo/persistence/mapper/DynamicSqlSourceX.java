package com.example.demo.persistence.mapper;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

public class DynamicSqlSourceX implements SqlSource {
    private final Configuration configuration;


    public DynamicSqlSourceX(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        Map<String, String> parames = (Map<String, String>) parameterObject;
        String scriptSql = "<script>" + parames.get("sql") + "</script>";
        parames.remove("sql");
        XPathParser parser = new XPathParser(scriptSql, false, configuration.getVariables(), new XMLMapperEntityResolver());
        XNode xNode = parser.evalNode("/script");
        XMLScriptBuilder xmlScriptBuilder = new XMLScriptBuilder(configuration, xNode);
        return xmlScriptBuilder.parseScriptNode().getBoundSql(parames);
    }
}
