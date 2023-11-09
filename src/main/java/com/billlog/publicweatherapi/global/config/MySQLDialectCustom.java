package com.billlog.publicweatherapi.global.config;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * .yml 파일에 hibernate.dialect 옵션에 해당 클래스명을 적어준다.
 * 기본제공하는 MySQL8Dialect가아닌 커스텀을 사용하기 때문
 */
public class MySQLDialectCustom extends MySQL8Dialect {
    public MySQLDialectCustom(){
        super();

        /**
         * 회사명조회시 fulltext 조회를 하기위한 mysql 함수 ( 'match' )를 등록하여 준다.
         */
        registerFunction(
                "match",
                new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "match(?1) against (?2 in boolean mode)")
        );
    }
}
