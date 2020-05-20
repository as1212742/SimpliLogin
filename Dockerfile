FROM node:latest

WORKDIR /use/src/app

COPY  package*.json ./

RUN npm install

COPY . .

EXPOSE 5000

CMD [ "npm" ,"start"]

