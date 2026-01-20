# Kafka Sample Project

このプロジェクトは、Spring Bootを使用してApache Kafkaと連携するサンプルアプリケーションです。Avroスキーマを使用したメッセージのシリアライズ・デシリアライズ、プロデューサーによるメッセージ送信、およびコンシューマーによるメッセージ受信（リトライ機能、DLT処理付き）を実証しています。

## ディレクトリ構造

主要なディレクトリとファイルの構成は以下の通りです。

```
d:\workspace\kafkasample\
├── src\
│   ├── main\
│   │   ├── avro\
│   │   │   └── user.avsc                      # Avroスキーマ定義
│   │   ├── java\
│   │   │   └── com\example\kafkasample\
│   │   │       ├── KafkasampleApplication.java # アプリケーションのエントリーポイント
│   │   │       ├── consumer\
│   │   │       │   ├── KafkaConsumer.java     # コンシューマーのインターフェース
│   │   │       │   └── impl\
│   │   │       │       └── KafkaConsumerImpl.java # コンシューマーの実装 (受信ロジック)
│   │   │       ├── producer\
│   │   │       │   ├── KafkaProducer.java     # プロデューサーのインターフェース
│   │   │       │   └── impl\
│   │   │       │       └── KafkaProducerImpl.java # プロデューサーの実装 (送信ロジック)
│   │   │       ├── service\
│   │   │       │   ├── KafkaConsumerService.java
│   │   │       │   ├── KafkaProducerService.java
│   │   │       │   └── impl\
│   │   │       │       ├── KafkaConsumerServiceImpl.java
│   │   │       │       └── KafkaProducerServiceImpl.java # 定期実行される送信サービス
│   │   │       ├── repository\                # (現在は空または未使用)
│   │   └── resources\
│   │       └── application.properties         # Kafka接続設定など
├── pom.xml                                    # Maven依存関係定義 (Avro, Kafka, Spring Boot)
├── docker-compose.yml                         # Kafka環境などのコンテナ構成定義
└── HELP.md                                    # Spring Boot生成時のヘルプ
```

## 各ファイルの役割

### 1. Avro Schema (`src/main/avro/`)
*   **user.avsc**: `User` オブジェクトのデータ構造を定義したAvroスキーマファイルです。`name` (String), `favorite_number` (int), `favorite_color` (String) などのフィールドを持ちます。ビルド時にJavaクラスが自動生成されます。

### 2. Consumer (`com.example.kafkasample.consumer`)
*   **KafkaConsumer.java**: コンシューマー処理のインターフェース定義です。
*   **KafkaConsumerImpl.java**: `KafkaConsumer` の実装クラスです。
    *   `@KafkaListener`: "user" トピックを購読します。
    *   受信したメッセージの処理は `KafkaConsumerService` に委譲します。
    *   **Retry & DLT**: `@RetryableTopic` を使用して、エラー時のリトライ処理（3回試行、バックオフあり）と、最終的に失敗した場合の Dead Letter Topic (DLT) への転送処理 (`@DltHandler`) が実装されています。

### 3. Producer (`com.example.kafkasample.producer`)
*   **KafkaProducer.java**: メッセージ送信のインターフェース定義です。
*   **KafkaProducerImpl.java**: `KafkaProducer` の実装クラスです。
    *   `KafkaTemplate` を使用して、Avro形式の `User` オブジェクトを "user" トピックに送信します。

### 4. Service (`com.example.kafkasample.service`)
*   **KafkaProducerService.java**: メッセージ送信を管理するサービスのインターフェースです。
*   **KafkaProducerServiceImpl.java**: `KafkaProducerService` の実装クラスです。
    *   `@Scheduled(fixedRate = 100)`: 100ミリ秒ごとに定期的に実行されます。
    *   ランダムなデータ（UUIDのName、ランダムな色、数値）を持つ `User` オブジェクトを生成し、`KafkaProducer` を通じて送信します。
    *   起動から10分経過すると送信を停止するロジックが含まれています。
*   **KafkaConsumerService.java**: メッセージ受信処理を管理するサービスのインターフェースです。
*   **KafkaConsumerServiceImpl.java**: `KafkaConsumerService` の実装クラスです。
    *   `KafkaConsumerImpl` から受け取った `User` オブジェクトのリストを処理（ログ出力、DB保存など）します。

### 5. Config (`src/main/resources/`)
*   **application.properties**: Kafkaブローカーのアドレス (`spring.kafka.bootstrap-servers`)、Schema RegistryのURL (`spring.kafka.properties.schema.registry.url`)、シリアライザー・デシリアライザーの設定などが記述されています。
