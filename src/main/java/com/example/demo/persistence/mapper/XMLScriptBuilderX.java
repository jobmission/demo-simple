package com.example.demo.persistence.mapper;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

public class XMLScriptBuilderX extends XMLScriptBuilder {
    private boolean updateFlag = false;

    public XMLScriptBuilderX(Configuration configuration, XNode context, boolean updateFlag) {
        super(configuration, context);
        this.updateFlag = updateFlag;
    }

    public XMLScriptBuilderX(Configuration configuration, XNode context, Class<?> parameterType, boolean updateFlag) {
        super(configuration, context, parameterType);
        this.updateFlag = updateFlag;
    }

    @Override
    public SqlSource parseScriptNode() {
        return new DynamicSqlSourceX(configuration, this.updateFlag);
    }
}
