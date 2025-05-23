# PersonalTasks 📋

**PersonalTasks** é uma aplicação Android desenvolvida em Kotlin no Android Studio, com persistência local utilizando a biblioteca Room (Jetpack). O app permite cadastrar, visualizar, editar e excluir tarefas pessoais.

---

## 🛠️ Tecnologias Utilizadas

- **Kotlin**
- **Android SDK (API 26+)**
- **Android Studio (LadyBug ou superior)**
- **Room (Jetpack)**
- **RecyclerView**
- **Arquitetura MVC**

---

## 🎯 Funcionalidades

### 📌 Tela 1 — Lista de Tarefas

- Lista com tarefas cadastradas, utilizando `RecyclerView`.
- Cada item exibe:
  - Título
  - Descrição
  - Data limite
- **Menu de Opções:**
  - "Nova Tarefa" → abre formulário de criação (Tela 2).
- **Menu de Contexto (clique longo):**
  - Editar tarefa
  - Excluir tarefa
  - Detalhes (somente visualização)

### ✏️ Tela 2 — Cadastro, Edição e Detalhes

- Campos:
  - Título (texto)
  - Descrição (texto)
  - Data limite (usando `DatePicker`)
- Botões:
  - **Salvar** → envia a tarefa para a Tela 1 via `Intent`
  - **Cancelar** → retorna à Tela 1 sem salvar dados
- Modo de visualização desabilita campos e oculta botão "Salvar"

---

## ▶️ Como executar o projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/PersonalTasks.git
   ```

2. Abra o projeto no **Android Studio (LadyBug ou superior)**

3. Execute em um emulador ou dispositivo físico com **Android 8.0+**

---

## 📂 Estrutura de Pastas (resumo)

```
com.example.personaltasks/
├── adapter/           # Adapter da RecyclerView
├── controller/        # Controller de tarefas (lógica de dados)
├── model/             # Data class Task + constantes e configuração do Room
├── ui/                # Activities principais (Main e TaskForm)
└── res/               # Layouts, strings, cores etc.
```

---

## 📹 Vídeo de Demonstração

[🔗 Link para o vídeo de execução (máx. 1 minuto)](https://drive.google.com/...) *(adicione o link após gravar)*

---

## ✅ Checklist de Entrega

- [x] App funcional com persistência local
- [x] Uso de Room
- [x] Navegação com Intent explícita
- [x] Tela de lista com menus (options/context)
- [x] Tela de formulário com `DatePicker`
- [x] README com instruções
- [ ] Vídeo de até 1 minuto
- [x] Commits com padrão convencional
- [x] Arquitetura MVC

---

## 👨‍💻 Autor

**João Marcelo Simão de Castro**  
[LinkedIn](https://br.linkedin.com/in/joao-marcelo-castro) · [GitHub](https://github.com/jyagami99)

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).