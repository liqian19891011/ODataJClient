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
package com.msopentech.odatajclient.engine.communication.request.cud;

import com.msopentech.odatajclient.engine.client.http.HttpClientException;
import com.msopentech.odatajclient.engine.client.response.ODataResponseImpl;
import com.msopentech.odatajclient.engine.communication.header.ODataHeader;
import com.msopentech.odatajclient.engine.communication.request.ODataBasicRequestImpl;
import com.msopentech.odatajclient.engine.communication.request.ODataRequestFactory;
import com.msopentech.odatajclient.engine.communication.request.batch.ODataBatchableRequest;
import com.msopentech.odatajclient.engine.communication.response.ODataDeleteResponse;
import com.msopentech.odatajclient.engine.types.ODataFormat;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

/**
 * This class implements an OData delete request.
 * Get instance by using ODataRequestFactory.
 *
 * @see ODataRequestFactory#getDeleteRequest(com.msopentech.odatajclient.engine.data.ODataURI)
 */
public class ODataDeleteRequest extends ODataBasicRequestImpl<ODataDeleteResponse, ODataFormat>
        implements ODataBatchableRequest {

    /**
     * Constructor.
     *
     * @param uri URI of the entity to be deleted.
     */
    ODataDeleteRequest(final URI uri) {
        // set method ... . If cofigured X-HTTP-METHOD header will be used.
        super(Method.DELETE, uri);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ODataDeleteResponse execute() {
        request.setHeader(ODataHeader.HeaderName.accept.toString(), getAccept());
        try {
            final HttpResponse res = client.execute(request);
            return new ODataDeleteResponseImpl(client, res);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } catch (RuntimeException e) {
            this.request.abort();
            throw new HttpClientException(e);
        }
    }

    /**
     * {@inheritDoc }
     * <p>
     * No payload: empty byte array will be returned.
     */
    @Override
    protected byte[] getPayload() {
        return new byte[0];
    }

    private static class ODataDeleteResponseImpl extends ODataResponseImpl implements ODataDeleteResponse {

        public ODataDeleteResponseImpl(final HttpClient client, final HttpResponse res) {
            super(client, res);
            this.close();
        }
    }
}
