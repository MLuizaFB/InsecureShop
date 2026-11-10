<h1 align="center">
  <br>
  <a href="https://www.insecureshopapp.com"><img src="https://static.wixstatic.com/media/8e4f0c_a5f14156f091434da73524c2ac7c53d3~mv2.png/v1/fill/w_269,h_269,al_c,lg_1,q_85/logo.webp" width="150px" alt="InsecureShop"></a>
</h1>

<h4 align="center">Insecureshop - An Intentionally Vulnerable Android Application</h4>

<p align="center">
<a href="https://github.com/hax0rgb/InsecureShop/issues"><img src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat"></a>
<a href="https://github.com/hax0rgb/InsecureShop/releases"><img src="https://img.shields.io/github/v/release/hax0rgb/InsecureShop"></a>
</p>

---

## Aviso de Autoria / Attribution Notice

Este projeto é um fork independente do repositório original [InsecureShop](https://github.com/hax0rgb/InsecureShop), criado por **hax0rgb**.

O código original foi utilizado como objeto de estudo para um Trabalho de Conclusão de Curso (TCC) em Engenharia de Software na Universidade Estadual de Ponta Grossa (UEPG). As intervenções e contribuições realizadas neste repositório incluem:

1. **Modernização do Ambiente de Compilação:** Atualização da esteira de build para suporte ao Android 14 (API 34), Android Gradle Plugin 8.0+, integração do Jetpack ViewBinding e suporte a versões modernas do Kotlin.
2. **Remediação de Vulnerabilidades (OWASP MASVS):** Refatoração prática de código e configurações nos domínios de armazenamento local (`MASVS-STORAGE`) e criptografia (`MASVS-CRYPTO`), incluindo:
   - Migração da camada de persistência para **Jetpack DataStore** com hashing (SHA-256) e salting (`SecureRandom`);
   - Segregação de identificadores sensíveis via `local.properties`, injeção em compilação (`BuildConfig`) e ofuscação pelo compilador R8;
   - Implementação de esteira de release com assinatura digital nos esquemas **v2 e v3** (RSA 2048 bits e SHA-256);
   - Definição de políticas restritivas de backup e transferência de dados (`data_extraction_rules.xml` e `backup_rules.xml`).

---

### English Summary
This repository is an independent fork of the original [InsecureShop](https://github.com/hax0rgb/InsecureShop) project by **hax0rgb**, developed as part of an undergraduate thesis in Software Engineering at the State University of Ponta Grossa (UEPG).

**Key Contributions:**
- Upgraded the legacy build pipeline to target Android 14 (API 34), AGP 8.0+, and modern Kotlin tools.
- Implemented practical code remediations aligned with the **OWASP MASVS** standard (`MASVS-STORAGE` and `MASVS-CRYPTO`), mitigating hardcoded identifiers, insecure plaintext storage, obsolete SHA-1 signing, and unrestricted backup policies.

---

<p align="center">
  <a href="#%EF%B8%8F-usage">Usage</a> •
  <a href="#-note">Note</a> •
  <a href="#%EF%B8%8Fvulnerabilities">Vulnerabilities</a> •
  <a href="#-guidance">Guidance</a> •
  <a href="#-credits">Credits</a>
</p>

InsecureShop is an Android application that is designed to be intentionally vulnerable. The application serves as a platform to test your Android pentesting skills. The vulnerabilities present in this app are real and have been found during mobile pentests.

## ⚙️ Usage

You can compile the source code in Android Studio or simply download the APK file from [here](https://github.com/hax0rgb/InsecureShop/releases)

## 📌 Note:

* Majority of the vulnerabilities can be exploited on a non-rooted device (Threat Actors - Remote users and Malicious third-party applications)
* No API's being used by the app.

## ❗️Vulnerabilities:

1. **Hardcoded Credentials:** Credentials are hardcoded somewhere that can be used to login to the application
2. **Insufficient URL Validation:** Possible to load any arbitrary URL in webview via Deeplink.
3. **Weak Host Validation Check:** Possible to bypass host validation check to load any arbitrary URL in webview.
4. **Arbitrary Code Execution:** Arbitrary Code Execution via third-party package contexts.
5. **Access to Protected Components:** The app takes an embedded Intent and passes it to method like startActivity. This allows any third party app to launch any protected component.
6. **Unprotected Data URIs:** The untrusted URI's passed via loadUrl method allows attackers to pass arbitrary URL in webview.
7. **Theft of Arbitrary:** Possible to steal files from app's local storage via ChooserActivity.
8. **Using Components with Known Vulnerabilities:** Identify the vulnerable components or libraries used in the app that can allow you to exfiltrate local files to remote domain.
9. **Insecure Broadcast Receiver:** An exported activity registers a broadcast during onCreate method execution. An attacker can trigger this broadcast and provide arbitrary URL in 'web_url' parameter.
10. **AWS Cognito Misconfiguration:** The misconfigured AWS cognito instance can be used to accesss AWS S3 bucket.
11. **Insecure use of FilePaths in FileProvider:** The use of wide file sharing declaration can be used to access root directory via content Provider.
12. **Use of Implicit intent to send a broadcast with sensitive data:** The use of Implicit intent can allow third-party apps to steal credentials.
13. **Intercepting Implicit intent to load arbitrary URL:** The use of Implicit intent can allow third-party apps to load any arbitrary URL in webview.
14. **Insecure Implementation of SetResult in exported Activity:** The insecure implementation used in ResultActivity can be used to access arbitrary content providers.
15. **Insecure Content Provider:** The content provider can be accessed by any third-party app to steal user credentials.
16. **Lack of SSL Certificate Validation:** The unsafe implementation of OnReceived SSL Error can be used to eavesdrop all the traffic loaded in webview.
17. **Insecure Webview Properties Enabled:** Insecure Webview properties are enabled that can allow third-party apps to exfiltrate local data to remote domain.
18. **Insecure Data Storage:** The app stores user credentials locally without encrypting them.
19. **Insecure Logging:** User credentials are leaked in logcat. Only attackers with physical access to the device can access this information.

## 🕵 Guidance:

The provided link doesn't provide you with solutions but can point you in the right direction:

https://docs.insecureshopapp.com

## 🙌 Credits:

* [Rujul Gandhi](https://www.linkedin.com/in/rujul-gandhi-3953337a/): Thank you for your contributions towards this app
* [Sergey Toshin](https://twitter.com/_bagipro) [(Oversecured)](https://oversecured.com): Thank you for your amazing research on Android security which prompted me to start this project
