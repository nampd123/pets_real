package com.group.group2.pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetEcommerceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetEcommerceSystemApplication.class, args);
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        String sql = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = CURRENT_SCHEMA AND LOWER(TABLE_NAME) = LOWER(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private boolean columnExists(Connection connection, String tableName, String columnName) throws SQLException {
        String sql = """
                SELECT 1
                FROM INFORMATION_SCHEMA.COLUMNS
                WHERE TABLE_SCHEMA = CURRENT_SCHEMA
                  AND LOWER(TABLE_NAME) = LOWER(?)
                  AND LOWER(COLUMN_NAME) = LOWER(?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            statement.setString(2, columnName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void ensurePetColumns(Connection connection, Statement statement) throws SQLException {
        if (!columnExists(connection, "pets", "quantity")) {
            statement.execute("ALTER TABLE pets ADD COLUMN quantity INT NOT NULL DEFAULT 1");
        }
        if (!columnExists(connection, "order_items", "quantity")) {
            statement.execute("ALTER TABLE order_items ADD COLUMN quantity INT NOT NULL DEFAULT 1");
        }
    }

    @Bean
    CommandLineRunner seedSampleData(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                if (!tableExists(connection, "users")) {
                    System.out.println("=== Schema not ready for seeding; skipping sample data import ===");
                    return;
                }

                ensurePetColumns(connection, statement);

                statement.execute("""
                        INSERT INTO users (id, full_name, email, phone, password_hash, role, status, created_at, updated_at) VALUES
                        ('11111111-1111-1111-1111-111111111111', 'Nguyen Van Admin', 'admin@petshop.local', '0900000001', '$2a$10$seedadminhash', 'ADMIN', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                        ('22222222-2222-2222-2222-222222222222', 'Tran Thi Owner', 'owner@petshop.local', '0900000002', '$2a$10$seedownerhash', 'SHOP_OWNER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                        ('33333333-3333-3333-3333-333333333333', 'Le Van Customer', 'customer@petshop.local', '0900000003', '$2a$10$seedcustomerhash', 'CUSTOMER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO shops (id, owner_id, name, description, phone, email, status, created_at, updated_at) VALUES
                        ('44444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222', 'Happy Paws Store', 'Cung cấp thú cưng, phụ kiện và dịch vụ chăm sóc thú cưng.', '02877778888', 'contact@happypaws.local', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO addresses (id, user_id, shop_id, receiver_name, phone, province, district, ward, detail_address, is_default, created_at) VALUES
                        ('55555555-5555-5555-5555-555555555555', '33333333-3333-3333-3333-333333333333', NULL, 'Le Van Customer', '0900000003', 'Ho Chi Minh', 'District 1', 'Ben Nghe', '12 Nguyen Hue', TRUE, CURRENT_TIMESTAMP),
                        ('66666666-6666-6666-6666-666666666666', NULL, '44444444-4444-4444-4444-444444444444', 'Happy Paws Store', '02877778888', 'Ho Chi Minh', 'District 3', 'Vo Thi Sau', '88 Le Van Sy', TRUE, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO pet_species (id, name, description, created_at) VALUES
                        ('77777777-7777-7777-7777-777777777771', 'Dog', 'Nhóm chó cảnh phổ biến.', CURRENT_TIMESTAMP),
                        ('77777777-7777-7777-7777-777777777772', 'Cat', 'Nhóm mèo cảnh phổ biến.', CURRENT_TIMESTAMP),
                        ('77777777-7777-7777-7777-777777777773', 'Thức ăn', 'Nhóm sản phẩm thức ăn cho thú cưng.', CURRENT_TIMESTAMP),
                        ('77777777-7777-7777-7777-777777777774', 'Phụ kiện', 'Nhóm sản phẩm phụ kiện cho thú cưng.', CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO pet_breeds (id, species_id, name, description, created_at) VALUES
                        ('88888888-8888-8888-8888-888888888881', '77777777-7777-7777-7777-777777777771', 'Poodle', 'Chó thông minh, dễ huấn luyện.', CURRENT_TIMESTAMP),
                        ('88888888-8888-8888-8888-888888888882', '77777777-7777-7777-7777-777777777771', 'Shiba Inu', 'Giống chó nhỏ, lanh lợi.', CURRENT_TIMESTAMP),
                        ('88888888-8888-8888-8888-888888888883', '77777777-7777-7777-7777-777777777772', 'British Shorthair', 'Mèo lông ngắn thân thiện.', CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO pets (id, shop_id, species_id, breed_id, name, gender, age_months, color, weight_kg, description, health_status, vaccination_status, price, status, quantity, created_at, updated_at) VALUES
                        ('99999999-9999-9999-9999-999999999991', '44444444-4444-4444-4444-444444444444', '77777777-7777-7777-7777-777777777771', '88888888-8888-8888-8888-888888888881', 'Milo', 'Male', 8, 'White', 3.20, 'Poodle con năng động, hợp gia đình.', 'Khỏe mạnh, đã kiểm tra thú y.', 'Đã tiêm đủ 2 mũi cơ bản.', 8500000, 'AVAILABLE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                        ('99999999-9999-9999-9999-999999999992', '44444444-4444-4444-4444-444444444444', '77777777-7777-7777-7777-777777777771', '88888888-8888-8888-8888-888888888882', 'Kuma', 'Female', 10, 'Red', 4.10, 'Shiba Inu lanh lợi, thích hợp nuôi trong nhà.', 'Khỏe mạnh, ăn uống tốt.', 'Đã tiêm phòng đầy đủ.', 12500000, 'AVAILABLE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                        ('99999999-9999-9999-9999-999999999993', '44444444-4444-4444-4444-444444444444', '77777777-7777-7777-7777-777777777772', '88888888-8888-8888-8888-888888888883', 'Luna', 'Female', 6, 'Gray', 2.80, 'Mèo British Shorthair hiền, dễ chăm.', 'Khỏe mạnh, lông bóng mượt.', 'Đã tiêm phòng và tẩy giun.', 7200000, 'AVAILABLE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO pet_images (id, pet_id, image_url, is_thumbnail, created_at) VALUES
                        ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '99999999-9999-9999-9999-999999999991', '/uploads/pets/milo-1.jpg', TRUE, CURRENT_TIMESTAMP),
                        ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '99999999-9999-9999-9999-999999999991', '/uploads/pets/milo-2.jpg', FALSE, CURRENT_TIMESTAMP),
                        ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', '99999999-9999-9999-9999-999999999992', '/uploads/pets/kuma-1.jpg', TRUE, CURRENT_TIMESTAMP),
                        ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', '99999999-9999-9999-9999-999999999993', '/uploads/pets/luna-1.jpg', TRUE, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO carts (id, user_id, created_at, updated_at) VALUES
                        ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '33333333-3333-3333-3333-333333333333', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO cart_items (id, cart_id, pet_id, quantity, price, created_at) VALUES
                        ('cccccccc-cccc-cccc-cccc-ccccccccccc1', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '99999999-9999-9999-9999-999999999991', 1, 8500000, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO orders (id, customer_id, shop_id, total_amount, shipping_fee, final_amount, receiver_name, receiver_phone, receiver_address, note, status, created_at, updated_at) VALUES
                        ('dddddddd-dddd-dddd-dddd-dddddddddddd', '33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444', 8500000, 50000, 8550000, 'Le Van Customer', '0900000003', '12 Nguyen Hue, Ben Nghe, District 1, Ho Chi Minh', 'Giao giờ hành chính.', 'PAID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO order_items (id, order_id, pet_id, pet_name, price, quantity, created_at) VALUES
                        ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeee1', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '99999999-9999-9999-9999-999999999991', 'Milo', 8500000, 1, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO payments (id, order_id, payment_method, amount, status, transaction_code, paid_at, created_at) VALUES
                        ('ffffffff-ffff-ffff-ffff-fffffffffff1', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'CASH', 8550000, 'SUCCESS', 'PAY-20260622-0001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO shipments (id, order_id, carrier_name, tracking_code, shipping_status, shipped_at, delivered_at, created_at, updated_at) VALUES
                        ('12121212-1212-1212-1212-121212121212', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'GHN', 'GHN-TRACK-0001', 'DELIVERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO reviews (id, order_id, customer_id, shop_id, rating, comment, created_at) VALUES
                        ('13131313-1313-1313-1313-131313131313', 'dddddddd-dddd-dddd-dddd-dddddddddddd', '33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444', 5, 'Thú cưng khỏe, shop hỗ trợ nhanh và rõ ràng.', CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                statement.execute("""
                        INSERT INTO favorites (id, user_id, pet_id, created_at) VALUES
                        ('14141414-1414-1414-1414-141414141414', '33333333-3333-3333-3333-333333333333', '99999999-9999-9999-9999-999999999992', CURRENT_TIMESTAMP)
                        ON CONFLICT (id) DO NOTHING
                        """);

                System.out.println("=== Seed completed ===");
                String[] tables = {"users", "shops", "pet_species", "pet_breeds", "pets", "pet_images", "carts", "cart_items", "orders", "order_items", "payments", "shipments", "reviews", "favorites"};
                for (String table : tables) {
                    try (ResultSet rs = statement.executeQuery("select count(*) from " + table)) {
                        rs.next();
                        System.out.printf("%s = %d%n", table, rs.getLong(1));
                    }
                }
            }
        };
    }

}
