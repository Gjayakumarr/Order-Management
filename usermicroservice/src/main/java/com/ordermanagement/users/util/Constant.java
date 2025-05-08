package com.ordermanagement.users.util;

public class Constant {

    public static final boolean TRUE = true;
    public static final boolean FALSE = false;

    public static class RESPONSE_MESSAGES{

        public static final String SUCCESS = "Data Retrieved";
        public static final String FAILURE  = "Data Not Retrieved";
        public static final String FAILURE_DELETE  = "Failed to Delete";
        public static final String SUCCESS_DELETE  = "Delete Success";
        public static final String BAD_REQUEST = "Parameters are not valid";
        public static final String CREATED = "Data Inserted";
        public static final String UPDATION   = "Data Updated";
        public static final String UPDATION_FAILED   = "Data Update Failed";
        public static final String INSERTION_FAILURE = "Data Not Inserted";

    }

    public static class RESPONSE_CODE {

        // 2xx Success Codes
        public static final int SUCCESS = 200;                      // OK
        public static final int CREATED = 201;                      // Created
        public static final int ACCEPTED = 202;                     // Accepted
        public static final int NO_CONTENT = 204;                   // No Content

        // 4xx Client Error Codes
        public static final int BAD_REQUEST = 400;                  // Bad Request
        public static final int UNAUTHORIZED = 401;                 // Unauthorized
        public static final int PAYMENT_REQUIRED = 402;             // Payment Required
        public static final int FORBIDDEN = 403;                    // Forbidden
        public static final int NOT_FOUND = 404;                    // Not Found
        public static final int METHOD_NOT_ALLOWED = 405;           // Method Not Allowed
        public static final int NOT_ACCEPTABLE = 406;               // Not Acceptable
        public static final int REQUEST_TIMEOUT = 408;              // Request Timeout
        public static final int CONFLICT = 409;                     // Conflict
        public static final int GONE = 410;                         // Gone
        public static final int LENGTH_REQUIRED = 411;              // Length Required
        public static final int PRECONDITION_FAILED = 412;          // Precondition Failed
        public static final int PAYLOAD_TOO_LARGE = 413;            // Payload Too Large
        public static final int URI_TOO_LONG = 414;                 // URI Too Long
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;       // Unsupported Media Type
        public static final int TOO_MANY_REQUESTS = 429;            // Too Many Requests

        // 5xx Server Error Codes
        public static final int INTERNAL_SERVER_ERROR = 500;        // Internal Server Error
        public static final int NOT_IMPLEMENTED = 501;              // Not Implemented
        public static final int BAD_GATEWAY = 502;                  // Bad Gateway
        public static final int SERVICE_UNAVAILABLE = 503;          // Service Unavailable
        public static final int GATEWAY_TIMEOUT = 504;              // Gateway Timeout
        public static final int HTTP_VERSION_NOT_SUPPORTED = 505;   // HTTP Version Not Supported
    }

}
