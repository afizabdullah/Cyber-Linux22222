# 🚀 دليل إعداد CI/CD لـ Cyber Linux OS (إصدارات APK فقط)

يوفر هذا المشروع مسار CI/CD جاهز للاستخدام باستخدام GitHub Actions لأتمتة بناء واختبار ونشر تطبيق Cyber Linux OS (APK).

## 🗂️ سير العمل (Workflows) المتوفرة:

1. **Build APK (`android-build-apk.yml`)**: 
   - يبني التطبيق (Debug/Release APK) مع كل `push` أو `pull_request` على فروع `main` و `develop`.
   - يقوم بتشغيل اختبارات Lint و Unit Tests.
2. **Release APK (`android-release-apk.yml`)**:
   - ينشئ إصداراً جديداً (Release) مع توقيع الـ APK ويرفعه إلى GitHub Releases.
3. **PR Check (`android-pr-check.yml`)**:
   - يتأكد من سلامة الكود والاختبارات قبل دمج أي Pull Request.

---

## ⚙️ متطلبات الإعداد (Secrets Required):

قم بإضافة الـ Secrets التالية في إعدادات مستودعك على GitHub:
`Settings -> Secrets and variables -> Actions -> New repository secret`

| اسم الـ Secret | الوصف |
| --- | --- |
| `KEYSTORE_BASE64` | ملف `release.keystore` محول إلى Base64 |
| `KEYSTORE_PASSWORD` | كلمة المرور الخاصة بالـ Keystore |
| `KEY_ALIAS` | اسم المستخدم (Alias) المستخدم في الـ Keystore |
| `KEY_PASSWORD` | كلمة المرور الخاصة بالـ Alias |

### 🔑 كيفية توليد Keystore وتحويله إلى Base64:

1. **إنشاء ملف Keystore**:
   ```bash
   keytool -genkey -v -keystore release.keystore -alias mykey -keyalg RSA -keysize 2048 -validity 10000
   ```
2. **تحويله إلى Base64**:
   ```bash
   base64 -w 0 release.keystore > keystore_base64.txt
   ```
   انسخ محتوى `keystore_base64.txt` وأضفه كقيمة لـ `KEYSTORE_BASE64` في GitHub Secrets.

---

## 🛠️ كيفية الاستخدام:

- **التحقق من الكود (PRs)**: تلقائياً عند إنشاء Pull Request.
- **بناء التطبيق (Build)**: تلقائياً عند الدفع (Push) إلى `main` أو `develop`.
- **نشر إصدار جديد (Release)**: تلقائياً عند إنشاء Release جديد عبر GitHub، أو يدوياً من تبويب Actions واختيار `Release APK - Cyber Linux OS`.

---

# 🚀 Cyber Linux OS CI/CD Setup Guide (APK Only)

This repository includes a production-ready CI/CD pipeline using GitHub Actions to automate building, testing, and deploying the Cyber Linux OS Android application (APK only).

## 🗂️ Available Workflows:

1. **Build APK (`android-build-apk.yml`)**: 
   - Builds the application (Debug/Release APK) on every `push` or `pull_request` to `main` and `develop`.
   - Runs Lint and Unit Tests.
2. **Release APK (`android-release-apk.yml`)**:
   - Builds a signed APK and automatically uploads it to GitHub Releases.
3. **PR Check (`android-pr-check.yml`)**:
   - Validates code health (lint and tests) before merging Pull Requests.

---

## ⚙️ Requirements (GitHub Secrets):

Add the following secrets in your GitHub repository:
`Settings -> Secrets and variables -> Actions -> New repository secret`

| Secret Name | Description |
| --- | --- |
| `KEYSTORE_BASE64` | Base64 encoded string of `release.keystore` |
| `KEYSTORE_PASSWORD` | Password for the Keystore |
| `KEY_ALIAS` | Alias used in the Keystore |
| `KEY_PASSWORD` | Password for the Alias |

### 🔑 How to Generate and Encode Keystore:

1. **Generate a Keystore file**:
   ```bash
   keytool -genkey -v -keystore release.keystore -alias mykey -keyalg RSA -keysize 2048 -validity 10000
   ```
2. **Encode to Base64**:
   ```bash
   base64 -w 0 release.keystore > keystore_base64.txt
   ```
   Copy the content of `keystore_base64.txt` and add it as the value for `KEYSTORE_BASE64` in GitHub Secrets.
