# My Top 100 in Samsung Music 🎵

<div align="center">
<a href="#">Leia em Português</a> | <a href="README.md">Read in English</a>
</div>
<div align="center">
<img src="screenshots/icon.png">
</div>

![GitHub License](https://img.shields.io/github/license/8126Lucas/top_from_samsung-music)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![GitHub issues](https://img.shields.io/github/issues/8126Lucas/top_from_samsung-music)
![GitHub stars](https://img.shields.io/github/stars/8126Lucas/top_from_samsung-music)

Uma **solução completa** que analisa as tuas músicas mais ouvidas: 
- 📱 App Android para recolha de dados
- 🌐 Webpage para visualização online


## 📸 Screenshots

|   Permissões   | Ficheiro Encontrado | Upload Bem Sucedido | Website |
|----------------|---------------------|---------------------|---------|
| ![perms](screenshots/permissions.jpg) | ![file](screenshots/file_found.jpg) | ![upload](screenshots/upload.jpg) | ![web](screenshots/web.png)

## 🚀 Funcionalidades

- ✅ Encontra ficheiros M3U automaticamente
- ✅ Extrai metadados das músicas (título, artista, álbum)
- ✅ Gera URLs do YouTube automaticamente usando título + artista para pesquisa
- ✅ Autentica com Firebase
- ✅ Upload seguro para a cloud

## 🌐 Webpage de Visualização

Além da aplicação Android, o projeto inclui uma webpage que mostra as músicas numa interface visual:

- ✅ Visualização em tempo real dos dados do Firebase
- ✅ Design responsivo para todos os dispositivos  
- ✅ Animações suaves com GSAP
- ✅ Links diretos para o YouTube
- ✅ Data da última atualização

**Acesso:** [Ver o meu top de músicas mais ouvidas](https://8126lucas.github.io/top_from_samsung-music/)


## 📱 Como Funciona

1. A app pede permissões necessárias
2. Procura ficheiros `MOST_LISTENED.m3u` no dispositivo
3. Extrai informações das músicas
4. Converte tudo para JSON
5. Faz upload para Firebase Storage

## 🔧 Tecnologias Utilizadas

### Aplicação Android
- **Android SDK** - Plataforma principal
- **Firebase** - Autenticação e Storage
- **Apache Tika** - Extração de metadados
- **Gson** - Conversão para JSON
- **WorkManager** - Tarefas em background
### Frontend Web
- **HTML5/CSS3** - Interface responsiva
- **JavaScript ES6** - Lógica da aplicação
- **Firebase SDK** - Conexão em tempo real
- **GSAP** - Animações e transições

## 📋 Pré-requisitos

- Android 13+ (API 33)
- Java 17
- Ficheiro M3U no dispositivo
- Conexão à internet/dados móveis

## ⚙️ Instalar a Aplicação

1. Clonar o repositório:
```bash
git clone https://github.com/8126Lucas/top_from_samsung-music.git
```
1. Iniciar um projeto no Firebase
2. Obter as chaves SHA-1 e SHA-256 do Firebase:
```bash
# No Windows
keytool -list -v -keystore %USERPROFILE%\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android
# Em Linux ou MacOS
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```
1. Introduzir as chaves no projeto Firebase:
```bash
"Configurações do projeto" > "Seus aplicativos" > "Aplicativos Android" > "Adicionar impressão digital"
```
5. Fazer download do ficheiro `google-services.json`
6. Guardar o `google-services.json` em `top_from_samsung-music/android_java/app/`
7. Definir as regras do Firebase
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read: if true;
      allow write: if request.auth != null;
    }
  }
}
```
8. Compilar e instalar no dispositivo

## 🌐 Configurar a Webpage

1. Fazer upload dos ficheiros `index.html`, `src/script.js`, `src/style.css` para um servidor web/GitHub Pages <br>
   - **Alternativa:** Executar `npm run dev` para testar localmente
2. Configurar as regras do Firebase (já definidas acima)
3. A webpage irá automaticamente buscar os dados mais recentes

**Nota:** Para usar com os teus próprios dados, substitui a configuração Firebase no `script.js` pela tua configuração pessoal.


## 📁 Estrutura do Projeto
```bash
top_from_samsung-music/
├── android_java/
│   └── app/
│       ├── src/main/java/com/lucas8126/top100insm/
│       │   ├── PermissionsHandler.java   # Gestão de permissões
│       │   ├── MusicProcessor.java       # Processamento principal
│       │   ├── CollectTop.java           # Serviço de principal
│       │   └── ...
│       └── google-services.json          # Configuração Firebase
└── web/
    ├── src
    │   ├── style.css
    │   ├── script.js
    │   └── ...
    └── index.html
```

## ⚠️ Problemas Conhecidos

- A app só funciona com ficheiros M3U da Samsung Music
- Requer reinicialização se as permissões forem negadas
- Metadata extraction pode falhar com ficheiros corrompidos

## 🛠️ Resolução de Problemas

**Q: A app não encontra o ficheiro M3U?**  
**A:** Certifica-te que a playlist tem o nome **MOST_LISTENED**.

**Q: O upload falha?**  
**A:** Verifica a ligação à internet e as configurações do Firebase. Se o telemóvel estiver no modo poupança de bateria, a aplicação só funcionará se tiver permitido o uso de bateria sem restrição.

## 👥 Autores
- [**Lucas Santos**](https://github.com/8126Lucas) - Desenvolvimento da aplicação Android e website
- **Joana Alves** - Design e interface visual

## 🤝 Como Contribuir

1. Faz um fork do projeto
2. Cria uma branch para a tua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit das tuas mudanças (`git commit -am 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abre um Pull Request