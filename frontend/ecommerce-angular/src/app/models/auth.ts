export interface AuthRequestBody {
  email: string,
  password: string
}

export interface AuthResponseBody {
  accessToken: string,
  refreshToken: string,
  tokenType: string,
  expiryDate: string
}
