# Gerador de Nota Fiscal

Este é um projeto Spring Boot desenvolvido para gerenciar a geração de notas fiscais, com foco em cálculos de tributos e processamento de pedidos.

O desafio tem como objetivo avaliar as minhas habilidade para um processo seletivo.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.2
- Maven
- Lombok
- Spring Web

## 📋 Pré-requisitos

- JDK 21
- Maven 3.6+
- IDE de sua preferência (recomendado: IntelliJ IDEA ou Eclipse)

## 🔧 Instalação

1. Clone o repositório ou descompacte o zip:
```bash
git clone geradornotafiscal (Apenas para documentar)
```

2. Navegue até o diretório do projeto:
```bash
cd geradornotafiscal
```

3. Compile o projeto:
```bash
mvn clean install
```

4. Execute a aplicação:
```bash
mvn spring-boot:run
```

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas (Clean Architecture):

- **Entrypoint**: Contém os controllers e recursos da API REST
- **Core**: Contém a lógica de negócio principal
- **Dataprovider**: Implementações de adaptadores e serviços externos
- **Config**: Configurações da aplicação

## 📦 Estrutura do Projeto

```
src/main/java/br/com/itau/geradornotafiscal/
├── config/           # Configurações da aplicação
├── core/            # Lógica de negócio principal
├── dataprovider/    # Implementações de serviços externos
└── entrypoint/      # Controllers e recursos da API
```

## 🛠️ Funcionalidades

- Geração de notas fiscais
- Cálculo de tributos
- Processamento de pedidos
- Integração com serviços externos (Simulações ou Mocks com sleep)

## 📝 Endpoints da API

### POST /api/pedido/gerarNotaFiscal
Gera uma nota fiscal para um pedido.

**Request Body:**
```json
{
    "id_pedido": 123,
    "data": "2024-03-20",
    "valor_total_itens": 1000.00,
    "valor_frete": 50.00,
    "itens": [...],
    "destinatario": {...}
}
```

## 🧪 Testes

Para executar os testes:
```bash
mvn test
```

## 🚀 Estratégia de Deploy (Blue-Green)

O projeto utiliza uma estratégia de deploy Blue-Green na AWS utilizando ECS Fargate para garantir zero downtime durante as atualizações.

### Infraestrutura AWS (Caso deseje acesso externo, acrescentar API Gateway)

- **ECS Fargate**: Para execução dos containers
- **Application Load Balancer (ALB)**: Para roteamento do tráfego
- **Route 53**: Para gerenciamento de DNS
- **CloudWatch**: Para monitoramento e logs

### Arquitetura Blue-Green

```
                    [Route 53]
                         |
                    [ALB]
                    /    \
              [Blue]    [Green]
              (v1.0)    (v1.1)
```

### Monitoramento

- **Métricas CloudWatch**:
  - CPU/Memory utilization
  - Request count
  - Error rates
  - Latency

- **Logs**:
  - Application logs
  - Container logs
  - ALB access logs

### Rollback

Em caso de problemas, o rollback é realizado revertendo o ALB para o target group anterior:


## 📫 Contato

Para mais informações, entre em contato através de [joao.amaral-souza@itau-unibanco.com.br]. 