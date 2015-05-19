package com.mulodo.miniblog.connection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("deprecation")
@Aspect
public class ConnectionInterceptor
{

    @Autowired
    private SessionFactory sessionFactory;

    @Around("@annotation(ReadOnlyConnection)")
    public Object proceed(ProceedingJoinPoint pjp) throws Throwable
    {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(session.connection().toString());
        ConnectionReadOnly readOnlyWork = new ConnectionReadOnly();
        ConnectionReadWrite readWriteWork = new ConnectionReadWrite();

        try {
            session.doWork(readOnlyWork);
            System.out.println(session.connection().toString());
            System.out.println("read only");
            return pjp.proceed();
        } finally {

            session.doWork(readWriteWork);
            System.out.println(session.connection().toString());
            System.out.println("read only switch back");
        }
    }

}
