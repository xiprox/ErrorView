/*
 * Copyright (C) 2015 Ihsan Isik
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

package tr.xip.errorview;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP error status codes.
 */
public class HttpStatusCodes {

    public static final int CODE_400 = 400;
    public static final int CODE_401 = 401;
    public static final int CODE_402 = 402;
    public static final int CODE_403 = 403;
    public static final int CODE_404 = 404;
    public static final int CODE_405 = 405;
    public static final int CODE_406 = 406;
    public static final int CODE_407 = 407;
    public static final int CODE_408 = 408;
    public static final int CODE_409 = 409;
    public static final int CODE_410 = 410;
    public static final int CODE_411 = 411;
    public static final int CODE_412 = 412;
    public static final int CODE_413 = 413;
    public static final int CODE_414 = 414;
    public static final int CODE_415 = 415;
    public static final int CODE_416 = 416;
    public static final int CODE_417 = 417;

    public static final int CODE_500 = 500;
    public static final int CODE_501 = 501;
    public static final int CODE_502 = 502;
    public static final int CODE_503 = 503;
    public static final int CODE_504 = 504;
    public static final int CODE_505 = 505;

    public static Map<Integer, String> getCodesMap() {
        Map<Integer, String> mCodes = new HashMap<>();

        mCodes.put(CODE_400, "Bad Request");
        mCodes.put(CODE_401, "Unauthorized");
        mCodes.put(CODE_402, "Payment Required");
        mCodes.put(CODE_403, "Forbidden");
        mCodes.put(CODE_404, "Not Found");
        mCodes.put(CODE_405, "Method Not Allowed");
        mCodes.put(CODE_406, "Not Acceptable");
        mCodes.put(CODE_407, "Proxy Authentication Required");
        mCodes.put(CODE_408, "Request Timeout");
        mCodes.put(CODE_409, "Conflict");
        mCodes.put(CODE_410, "Gone");
        mCodes.put(CODE_411, "Length Required");
        mCodes.put(CODE_412, "Precondition Failed");
        mCodes.put(CODE_413, "Request Entity Too Large");
        mCodes.put(CODE_414, "Request-URI Too Long");
        mCodes.put(CODE_415, "Unsupported Media Type");
        mCodes.put(CODE_416, "Requested Range Not Satisfiable");
        mCodes.put(CODE_417, "Expectation Failed");

        mCodes.put(CODE_500, "Internal Server Error");
        mCodes.put(CODE_501, "Not Implemented");
        mCodes.put(CODE_502, "Bad Gateway");
        mCodes.put(CODE_503, "Service Unavailable");
        mCodes.put(CODE_504, "Gateway Timeout");
        mCodes.put(CODE_505, "HTTP Version Not Supported");

        return mCodes;
    }
}