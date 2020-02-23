# Tradutor de Números Inteiros

Consiste em traduzir um número inteiro entre -99999 e 99999 
para portugues escrito por extenso.

- Microserviço: spring-boot

## Testes
Para rodar o programa de testes unitários execute:
```
mvn test
```

## Compilar
```
mvn compile
```

## Empacotar jar
```
mvn package
```

## Executar serviço
```
cd target
java -jar numextenso-0.0.1-SNAPSHOT.jar
```

## Exemplo
```
curl http://localhost:3000/1
curl http://localhost:3000/-1042
curl http://localhost:3000/94587
```

## Criar imagem Docker
```
docker build -f src/main/docker/Dockerfile.jvm -t bucker/traduzir-numero-jvm .
```

## Executar container Docker
```
docker run -dti --rm -p 3000:3000 bucker/traduzir-numero-jvm
```

