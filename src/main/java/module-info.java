module Hibernate.JPA.Derby.Demo.Application.main {

    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;

    requires org.slf4j;
    requires org.apache.logging.log4j;

    requires java.persistence;
    requires java.sql;

    requires hibernate.entitymanager;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    exports application to javafx.graphics;

    opens view to javafx.fxml;
    opens model to org.hibernate.orm.core;

}