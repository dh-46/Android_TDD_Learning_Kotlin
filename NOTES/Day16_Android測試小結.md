# Day16 - Android 測試小結

## Android的各種測試

1. Local Unit Test
2. Local Unit Test by Robolectric
3. Instrumented Test
4. UI Test

### Local Unit Test

- 執行速度最快
- 使用JVM
- 專案中的商業邏輯與View要解耦合
- 專案中的測試應該盡可能使用Unit Test

### Local Unit Test with Robolectric

- 透過Robolectric框架，使用單元測試的方式來測Instrumented Test

### Instrumented Test

- 當被測試與Android Framework相關時
- 需要模擬機或實體手機
- 速度較慢

### UI Test

- 測試UI與使用者的互動
- 需要模擬機或實體手機

### Instrumented Test與UI Test的比較

- 都寫在androidTest的目錄下
- 都需要模擬機或實體手機
- 兩者差異在於UI Test更強調使用者的互動(View)

### UI Test (Espresso) 與 Robolectric 的比較

#### 測試目錄

- UI Test: \androidTest\
- Robolectric: \test\

#### 平台

- UI Test: 模擬機或實體手機
- Robolectric: JVM

#### 除錯

- UI Test: 較難找到失敗原因
- Robolectric: 屬於單元測試，除錯較快。

#### 測試行為 (在驗證註冊的範例中)

- UI Test: 是驗證註冊成功後，是否有在畫面上看到註冊成功的文字。
- Robolectric: 驗證傳給Intent的值是否正確。

## 資料來源

[Day16 - Android 測試小結 ](https://ithelp.ithome.com.tw/articles/10222724)