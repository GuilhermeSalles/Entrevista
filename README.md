
# Banco de Transferências 💳
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/GuilhermeSalles/Entrevista/blob/main/LICENSE) 

## Descrição 📚

Este projeto simula um sistema bancário simples com operações de **crédito**, **débito** e **transferência** entre contas bancárias. A principal funcionalidade implementada é a **transferência entre contas**, que inclui o uso de **bloqueio pessimista** para evitar inconsistências de saldo em operações concorrentes.

### O que é o bloqueio pessimista? 🔒
Em um ambiente de transações concorrentes, como em sistemas bancários, é importante garantir que uma operação de **transferência** não interfira ou seja interferida por outra operação simultânea. O **bloqueio pessimista** é utilizado para garantir que, durante a verificação e a modificação do saldo das contas envolvidas, nenhuma outra transação consiga acessar as mesmas contas até que a transação atual seja completada.

Este bloqueio impede que outras transações leiam ou modifiquem os dados das contas enquanto a operação de **transferência** estiver em andamento. Isso evita, por exemplo, a situação onde uma conta poderia ter seu saldo alterado negativamente devido à concorrência de outras transações.

## 🚀 Tecnologias Usadas
- **Spring Boot** - Framework principal para a construção do backend. ⚙️
- **JPA/Hibernate** - Para persistência dos dados no banco de dados. 💾
- **Thymeleaf** - Tecnologia para integração do backend com o frontend. 📑
- **MySQL / H2 Database** - Banco de dados utilizado para armazenar as contas e transações. 🗃️
- **JUnit 5** - Para testes unitários. 🧪

## Funcionalidades 💡

1. **Criação de Conta Bancária**:
   - Permite criar novas contas bancárias com número, titular e saldo inicial.

2. **Crédito em Conta**:
   - Permite creditar valores nas contas bancárias de acordo com o ID da conta e valor informado.

3. **Débito de Conta**:
   - Permite debitar valores de uma conta, com verificação de saldo insuficiente.

4. **Transferência entre Contas**:
   - A operação de transferência entre duas contas é realizada com **bloqueio pessimista** para garantir que não ocorram inconsistências no saldo durante transações concorrentes.

## ⚙️ Funcionalidade de Transferência

A transferência entre contas foi implementada com **bloqueio pessimista** para evitar inconsistências de saldo, garantindo que transações simultâneas não resultem em erros de concorrência. 🔒

### Bloqueio Pessimista

O bloqueio pessimista foi utilizado para garantir que, quando uma conta está sendo transferida, outras operações não possam interferir até que a transação seja concluída. Isso evita o problema de concorrência em transações simultâneas. 🚫

### Teste Unitário 🧪

Além disso, foi criado um **teste unitário** para verificar a funcionalidade da transferência entre contas, validando o comportamento da aplicação, como a verificação de saldo suficiente e a integridade das contas após a transação. ✔️

## 📂 Frontend

A aplicação também possui integração com o **frontend** utilizando **Thymeleaf**. O usuário tem acesso a uma interface simples para realizar as transações, depósitos e saques. O frontend foi desenvolvido com foco na **usabilidade** e **design simples**, garantindo que todas as operações sejam realizadas de forma intuitiva. 💻

## 🔄 Transações Simultâneas

O sistema foi projetado para gerenciar **transações simultâneas** de forma segura, utilizando o bloqueio pessimista. Isso significa que quando uma conta está sendo utilizada em uma transação, outras tentativas de acesso simultâneo a essa conta serão bloqueadas até a conclusão da operação. 🔄

---

## Como Funciona a Transferência Bancária com Bloqueio Pessimista? 🤔

O fluxo da operação de **transferência** entre contas é o seguinte:

1. O usuário inicia a transferência entre duas contas.
2. O **bloqueio pessimista** é aplicado nas contas envolvidas, garantindo que nenhuma outra operação possa modificar os dados dessas contas durante a transferência.
3. A verificação do saldo da conta de origem é realizada para garantir que há fundos suficientes.
4. O saldo da conta de origem é reduzido pelo valor da transferência.
5. O saldo da conta de destino é incrementado com o valor da transferência.
6. Ambas as contas são atualizadas no banco de dados e a transação é finalizada.

Com o bloqueio pessimista, garantimos que outras transações não poderão acessar essas contas enquanto a transferência estiver em andamento, evitando que o saldo da conta de origem seja alterado simultaneamente por outra operação.

---

## Como Executar o Projeto 🚀

1. **Clonar o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   ```

2. **Abrir o Projeto no IntelliJ ou outra IDE de sua escolha**.

3. **Rodar a Aplicação**:
   - Na raiz do projeto, execute o comando:
     ```bash
     ./mvnw spring-boot:run
     ```

4. Acesse a aplicação em [http://localhost:8080](http://localhost:8080) para testar as funcionalidades.

---

## Exemplos de Endpoints 🔗

### **1. Criar Conta Bancária**
- **Endpoint**: `POST /api/accounts/admin/create`
- **Corpo da Requisição**:
  ```json
  {
      "accountNumber": "123456",
      "ownerName": "João Silva",
      "balance": 1000.00
  }
  ```

### **2. Realizar Transferência**
- **Endpoint**: `POST /api/accounts/{fromAccountId}/transfer/{toAccountId}`
- **Parâmetros**:
  - `fromAccountId`: ID da conta de origem.
  - `toAccountId`: ID da conta de destino.
  - **Body**:
    ```json
    {
        "amount": 200.00
    }
    ```

### **3. Realizar Crédito em Conta**
- **Endpoint**: `PATCH /api/accounts/{accountId}/credit`
- **Parâmetro**: `accountId` (ID da conta).
- **Body**:
  ```json
  {
      "amount": 500.00
  }
  ```

### **4. Realizar Débito em Conta**
- **Endpoint**: `PATCH /api/accounts/{accountId}/debit`
- **Parâmetro**: `accountId` (ID da conta).
- **Body**:
  ```json
  {
      "amount": 300.00
  }
  ```

---


## 👨‍💻 Autor
**Guilherme Baltazar Vericimo de Sales**

<a href="https://www.linkedin.com/in/guilherme-baltazar-0028361a1" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a> 
<a href="https://instagram.com/yguilhermeb" target="_blank"><img src="https://img.shields.io/badge/-Instagram-%23E4405F?style=for-the-badge&logo=instagram&logoColor=white" target="_blank"></a>
