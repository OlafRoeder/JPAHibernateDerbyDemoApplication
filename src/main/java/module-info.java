module Hibernate.JPA.Derby.Demo.Application.main {

    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;

    requires org.slf4j;
    requires org.apache.logging.log4j;

    requires jakarta.persistence;
    requires java.sql;

    requires org.hibernate.orm.core;

    exports application to javafx.graphics;

    opens view to javafx.fxml;
    opens model to org.hibernate.orm.core;

}
