# Android上的測試

## Android的測試有三種

- Local Unit Test
- Instrumented Unit Test
- UI Test

### Local Unit Test

只在本地機器執行，類別編譯後就在JVM(Java Virtual Machine)上執行。
不依賴於Android框架，也不需要裝APK，執行速度快。

### Instrumented Unit Test

測試需要Android Framework。
同樣為單元測試，只是需要Android裝置或模擬器上執行，執行速度慢。

### UI Test

驗證使用者的操作行為(UI行為)，
一定會使用到Android Framework、UI，
所以一定會裝到Android裝置或模擬機。
使用Espresso框架測試。

## 測試金字塔

由下至上(由小至大)

1. Unit Test @Small
2. Integration Test @Medium
3. e2e Test @Large

> Google 建議 70% small, 20 % medium, and 10 % large

[資料來源](https://ithelp.ithome.com.tw/articles/10218979)