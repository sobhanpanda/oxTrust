package org.gluu.oxtrust.service.scim2.interceptor;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by jgomer on 2017-09-28.
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface RejectFilterParam {}
