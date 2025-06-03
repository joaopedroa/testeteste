# Gerador de Nota Fiscal

Este é um projeto Spring Boot desenvolvido para gerenciar a geração de notas fiscais, com foco em cálculos de tributos e processamento de pedidos.

O desafio tem como objetivo avaliar as minhas habilidade para um processo seletivo.

## 📐 Arquitetura do Sistema

O diagrama abaixo representa a arquitetura do sistema, incluindo os componentes principais e suas interações:

![Arquitetura do Sistema](itau_nf.drawio.svg)

### Componentes Principais

1. **Entrypoint**
   - GeradorNFResource: Endpoint REST para geração de notas fiscais
   - Validação de entrada e transformação de dados

2. **Core**
   - GeradorNotaFiscalUseCase: Lógica de negócio principal
   - Estratégias de processamento de notas fiscais
   - Cálculos de tributos

3. **Dataprovider**
   - Adaptadores para serviços externos
   - Simulações de integrações

4. **Config**
   - Configurações da aplicação
   - Beans e dependências

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

## 📊 Teste de Carga Simples

O projeto utiliza K6 para realizar testes de carga básicos no endpoint de geração de nota fiscal.

### Instalação do K6

```bash
# Windows (usando chocolatey)
choco install k6

# Linux
sudo apt-get install k6
```

### Script de Teste (load-test.js)

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 0 },    // Rampa de subida: 0 a 10 usuários em 30s
    { duration: '1m', target: 10 },    // Mantém 10 usuários por 1 minuto
    { duration: '30s', target: 0 },    // Rampa de descida: 10 a 0 usuários em 30s
  ],
  thresholds: {
    http_req_duration: ['p(95)<600'], // 95% das requisições devem completar em 600ms
    http_req_failed: ['rate<0.01'],   // Taxa de erro deve ser menor que 1%
  },
};

export default function () {
  const payload = JSON.stringify({
    id_pedido: 123,
    data: "2024-03-20",
    valor_total_itens: 1000.00,
    valor_frete: 50.00,
    itens: [],
    destinatario: {}
  });

  const response = http.post('http://localhost:8080/api/pedido/gerarNotaFiscal', payload, {
    headers: { 'Content-Type': 'application/json' },
  });

  check(response, {
    'status is 200': (r) => r.status === 200,
    'response time < 200ms': (r) => r.timings.duration < 600,
  });

  sleep(1);
}
```

### Executando o Teste

```bash
k6 run load-test.js
```

### Explicação das Etapas (Stages)

1. **Rampa de Subida (30s)**:
   - Começa com 0 usuários
   - Aumenta gradualmente até 10 usuários
   - Duração: 30 segundos

2. **Platô (1m)**:
   - Mantém 10 usuários constantes
   - Duração: 1 minuto

3. **Rampa de Descida (30s)**:
   - Diminui gradualmente de 10 para 0 usuários
   - Duração: 30 segundos

### Resultados

O teste irá mostrar:
- Número de requisições
- Tempo médio de resposta
- Taxa de erros
- Requisições por segundo

Exemplo de saída:
```
running (30.0s), 00/10 VUs, 300 complete and 0 interrupted iterations
default ✓ [======================================] 10 VUs  30s

     data_received..................: 1.0 MB  33 kB/s
     data_sent......................: 1.0 MB  33 kB/s
     http_req_blocked...............: avg=1.5ms   min=0s      med=1ms     max=10ms    p(90)=2ms     p(95)=3ms    
     http_req_connecting............: avg=1ms     min=0s      med=1ms     max=8ms     p(90)=2ms     p(95)=2ms    
     http_req_duration..............: avg=150ms   min=100ms   med=140ms   max=300ms   p(90)=200ms   p(95)=250ms  
     http_req_failed................: 0.00%   ✓
     http_req_receiving.............: avg=2ms     min=1ms     med=2ms     max=5ms     p(90)=3ms     p(95)=4ms    
     http_req_sending...............: avg=1ms     min=0s      med=1ms     max=3ms     p(90)=2ms     p(95)=2ms    
     http_req_waiting...............: avg=147ms   min=98ms    med=137ms   max=295ms   p(90)=197ms   p(95)=247ms  
     http_reqs......................: 300    10.0/s
     iteration_duration.............: avg=1.15s   min=1.1s    med=1.14s   max=1.3s    p(90)=1.2s    p(95)=1.25s  
     iterations.....................: 300    10.0/s
     vus............................: 10     min=10   max=10
     vus_max........................: 10     min=10   max=10
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