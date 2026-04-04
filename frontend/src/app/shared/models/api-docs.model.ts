export interface ApiCategory {
  name: string;
  basePath: string;      // The domain + prefix: "http://localhost:8080/api/v1/auth"
  description: string;
  endpoints: ApiEndpoint[];
}

export interface ApiEndpoint {
  id: string;
  path: string;
  title: string;
  method: HttpMethod;
  description: string;
  bodyParams?: ApiParam[];
  headers?: ApiHeader[];
  parameters?: ApiParamater[];
  responses: ApiResponse[];
  authenticated?: boolean;
  rateLimit: number;
}

export interface ApiParam {
  name: string;
  type: 'string' | 'number' | 'boolean' | 'uuid' | 'object' | 'array';
  description: string;
  exampleValue?: string | number | boolean;
  required?: boolean;
}

export interface ApiHeader {
  name: string;
  type: 'string' | 'number' | 'boolean' | 'uuid' | 'object' | 'array';
  description: string;
  exampleValue?: string | number | boolean;
  required?: boolean;
}

export interface ApiParamater {
  name: string;
  type: 'string' | 'number' | 'boolean' | 'uuid' | 'object' | 'array';
  description: string;
  exampleValue?: string | number | boolean;
  required?: boolean;
}

/**
 * Example response structures
 */
export interface ApiResponse {
  code: number;
  title: string;
  description: string;
  exampleJson: string;
}

export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH' | 'WS';
