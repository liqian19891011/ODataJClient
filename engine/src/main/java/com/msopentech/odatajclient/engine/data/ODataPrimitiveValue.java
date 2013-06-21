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
package com.msopentech.odatajclient.engine.data;

import com.msopentech.odatajclient.engine.data.metadata.edm.EdmSimpleType;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

/**
 * OData primitive property value.
 */
public class ODataPrimitiveValue extends ODataValue {

    private static final long serialVersionUID = 2841837627899878223L;

    /**
     * Actual value.
     */
    final Serializable value;

    /**
     * Value type.
     */
    final EdmSimpleType type;

    /**
     * Constructor.
     *
     * @param value actual value.
     * @param type primitive value type.
     */
    public ODataPrimitiveValue(final Serializable value, final EdmSimpleType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Constructor.
     *
     * @param value actual value.
     * @param typeName primitive value type.
     */
    public ODataPrimitiveValue(final Serializable value, final String typeName) {
        this.value = value;
        this.type = StringUtils.isBlank(typeName) ? null : EdmSimpleType.valueOf(typeName);
    }

    /**
     * Gets type name.
     *
     * @return type name.
     */
    public String getTypeName() {
        return type == null ? null : type.toString();
    }

    @Override
    public String toString() {
        return value == null ? null : value.toString();
    }
}
