#!/bin/sh

mypath=`/bin/pwd`

cd ~/glassfish3-2/bin/

./asadmin start-domain

./asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property PortNumber=3306:Password=bookcase:User=bookcase:ServerName=localhost:DatabaseName=bookcase:URL=jdbc\\:mysql\\://localhost\\:3306/bookcase:driverClass=com.mysql.jdbc.Driver mysql_bookcase_bookcasePool

./asadmin create-jdbc-resource --connectionpoolid mysql_bookcase_bookcasePool mysql/bookcase

./asadmin create-auth-realm --classname com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property user-name-column=email:password-column=password:group-name-column=groupName:jaas-context=jdbcRealm:datasource-jndi="mysql/bookcase":group-table=viewLogin:user-table=viewLogin:digest-algorithm=none BookcaseRealm

./asadmin create-jms-resource --restype javax.jms.QueueConnectionFactory jms/bookcaseMailFactory

./asadmin create-jms-resource --restype javax.jms.Queue jms/bookcaseMail

./asadmin create-javamail-resource --mailhost smtp.gmail.com --mailuser bookcase@cerevka.cz --fromaddress bookcase@cerevka.cz --storeprotocol imap --storeprotocolclass com.sun.mail.imap.IMAPStore --transprotocol smtps --transprotocolclass com.sun.mail.smtp.SMTPSSLTransport --property mail-smtps-auth=true:mail-smtps-password=bookcase123 mail/bookcase

./asadmin deploy ${mypath}/Bookcase.ear
