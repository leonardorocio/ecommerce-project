export interface APIDocs {
  tags: Tag[];
  paths: Path;
  components: Component;
}

export type Method = 'get' | 'put' | 'delete' | 'patch' | 'post';

export type IMethod = {
  [method in Method]: MethodProperties;
};

export interface Path {
  [key: string]: IMethod;
}

export interface MethodProperties {
  tags: string[];
  parameters: Parameters[];
  requestBody: {};
  operationId: string;
  summary: string;
  description: string;
}

export interface Parameters {
  name: string;
  in: string;
  schema: {
    type: string;
    default: string;
    format: string;
  };
}

export interface Tag {
  name: string;
  description: string;
}

export interface Schema {
  [key: string]: SchemaProperties;
}

export interface SchemaProperties {
  properties: PropertyAsKey;
  required: [];
}

export interface PropertyAsKey {
  [key: string]: PropertyAttributes;
}

export interface PropertyAttributes {
  type: string;
  description: string;
  format: string;
  example: string;
  $ref: string
}

export interface Component {
  schemas: Schema;
}
