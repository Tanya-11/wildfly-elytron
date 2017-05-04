/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.security.sasl.external;

import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;

import org.kohsuke.MetaInfServices;
import org.wildfly.security.sasl.WildFlySasl;
import org.wildfly.security.sasl.util.SaslMechanismInformation;
import org.wildfly.security.util._private.Arrays2;

/**
 * Implementation of the SASL {@code EXTERNAL} server mechanism.  See <a href="https://tools.ietf.org/html/rfc4422#appendix-A">RFC 4422
 * appendix A</a> for more information about the {@code EXTERNAL} mechanism.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
@MetaInfServices(SaslServerFactory.class)
public final class ExternalSaslServerFactory implements SaslServerFactory {

    public SaslServer createSaslServer(final String mechanism, final String protocol, final String serverName, final Map<String, ?> props, final CallbackHandler cbh) throws SaslException {
        return mechanism.equals(SaslMechanismInformation.Names.EXTERNAL) && getMechanismNames(props, false).length != 0 ? new ExternalSaslServer(cbh) : null;
    }

    private String[] getMechanismNames(final Map<String, ?> props, boolean query) {
        if (props == null) {
            return Arrays2.of(SaslMechanismInformation.Names.EXTERNAL);
        }
        if ("true".equals(props.get(WildFlySasl.MECHANISM_QUERY_ALL)) && query) {
            return Arrays2.of(SaslMechanismInformation.Names.EXTERNAL);
        }
        if ("true".equals(props.get(Sasl.POLICY_NOACTIVE))) {
            return WildFlySasl.NO_NAMES;
        }
        if ("true".equals(props.get(Sasl.POLICY_FORWARD_SECRECY))) {
            return WildFlySasl.NO_NAMES;
        }
        if ("true".equals(props.get(Sasl.POLICY_NOPLAINTEXT))) {
            return WildFlySasl.NO_NAMES;
        }
        if ("true".equals(props.get(Sasl.POLICY_NOANONYMOUS))) {
            return WildFlySasl.NO_NAMES;
        }
        if ("true".equals(props.get(Sasl.POLICY_NODICTIONARY))) {
            return WildFlySasl.NO_NAMES;
        }
        if ("true".equals(props.get(Sasl.POLICY_PASS_CREDENTIALS))) {
            return WildFlySasl.NO_NAMES;
        }
        return Arrays2.of(SaslMechanismInformation.Names.EXTERNAL);
    }

    @Override
    public String[] getMechanismNames(Map<String, ?> props) {
        return getMechanismNames(props, true);
    }

}
