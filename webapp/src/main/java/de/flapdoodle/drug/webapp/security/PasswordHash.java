/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.drug.webapp.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;

public class PasswordHash {

	static Set<String> secureHashes = Sets.newHashSet("2f93a9ccdad674c0d84d50b5656e3576d9356906",
			"d92ec49ebb3658e4580d0f3025c8604d4634f36d", "81aea39f495952f97f2d69422c66595bbd959801",
			"e3e5533231212342effb9c98cfd5a58152529cd0");

	public static String createHash(String user, String password) {
		try {
			MessageDigest cript = MessageDigest.getInstance("SHA-1");
			cript.reset();
			cript.update(user.getBytes(Charsets.UTF_8));
			cript.update(password.getBytes(Charsets.UTF_8));
			return new BigInteger(1, cript.digest()).toString(16);
		} catch (NoSuchAlgorithmException nsx) {
			throw new RuntimeException(nsx);
		}
	}

	public static boolean isSecure(String hash) {
		return secureHashes.contains(hash);
	}
}
