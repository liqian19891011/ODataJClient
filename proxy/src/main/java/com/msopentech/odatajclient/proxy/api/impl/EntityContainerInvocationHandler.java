/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.proxy.api.impl;

import com.msopentech.odatajclient.proxy.api.annotations.EntitySet;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class EntityContainerInvocationHandler implements InvocationHandler {

    private final String serviceRoot;

    private final String schemaName;

    private final String entityContainerName;

    public EntityContainerInvocationHandler(final String serviceRoot,
            final String schemaName, final String entityContainerName) {

        this.serviceRoot = serviceRoot;
        this.schemaName = schemaName;
        this.entityContainerName = entityContainerName;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final Annotation[] methodAnnots = method.getAnnotations();
        // 1. access top-level entity sets
        if (methodAnnots.length == 0) {
            final Class<?> returnType = method.getReturnType();

            final Annotation annotation = returnType.getAnnotation(EntitySet.class);
            if (!(annotation instanceof EntitySet)) {
                throw new IllegalArgumentException("Return type " + returnType.getName()
                        + " is not annotated as @" + EntitySet.class.getSimpleName());
            }
            final String entitySetName = ((EntitySet) annotation).value();

            final Type[] abstractEntitySetParams =
                    ((ParameterizedType) returnType.getGenericInterfaces()[0]).getActualTypeArguments();

            return Proxy.newProxyInstance(returnType.getClassLoader(), new Class<?>[] {returnType},
                    EntitySetInvocationHandler.getInstance(
                    (Class<Serializable>) abstractEntitySetParams[0], (Class<Serializable>) abstractEntitySetParams[1],
                    serviceRoot, entitySetName));
        } // 2. invoke function imports
        else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}