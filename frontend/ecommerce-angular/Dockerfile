FROM node:18.16 as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
COPY --from=node /app/dist/ecommerce-angular/ /usr/share/nginx/html
