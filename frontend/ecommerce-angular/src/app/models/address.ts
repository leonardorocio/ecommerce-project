export interface Address {
  id: number,
  zipCode: string,
  city: string,
  state: string,
  street: string,
  number: number,
  complement: string,
  userId: number
}

export interface CEPQuery {
  cep: string,
  uf: string,
  localidade: string,
  logradouro: string,
  complemento: string
}

export interface CityResponse {
  id: number,
  nome: string
}

export interface StateResponse {
  id: number,
  nome: string,
  sigla: string
}
