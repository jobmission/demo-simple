package com.example.demo.persistence.mapper;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

public class XMLScriptBuilderX extends XMLScriptBuilder {

    public XMLScriptBuilderX(Configuration configuration, XNode context) {
        super(configuration, context);
    }

    public XMLScriptBuilderX(Configuration configuration, XNode context, Class<?> parameterType) {
        super(configuration, context, parameterType);
    }

    @Override
    public SqlSource parseScriptNode() {
        return new DynamicSqlSourceX(configuration);
    }
}
