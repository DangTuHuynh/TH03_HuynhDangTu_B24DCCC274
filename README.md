# Ứng dụng Quản lý Lịch hẹn (Appointment Manager)
Đây là một ứng dụng di động trên nền tảng Android, giúp người dùng ghi lại, quản lý các lịch hẹn và cuộc họp cá nhân kèm theo thông tin về địa điểm, thời gian và tính năng nhắc nhở.

## Mục đích của ứng dụng
Ứng dụng được thiết kế nhằm mục đích hỗ trợ người dùng theo dõi và quản lý thời gian hiệu quả. Thông qua giao diện trực quan và đơn giản, người dùng có thể:
- Đăng nhập an toàn để quản lý dữ liệu cá nhân.
- Xem danh sách tất cả các lịch hẹn sắp tới, được tự động sắp xếp theo thời gian.
- Xem thống kê nhanh về tổng số lịch hẹn và số lượng lịch hẹn có đặt lịch nhắc nhở.
- Thêm mới các lịch hẹn với đầy đủ thông tin (Tiêu đề, địa điểm, ngày, giờ, tùy chọn nhắc nhở).
- Xem chi tiết một lịch hẹn cụ thể.
- Cập nhật trạng thái nhắc nhở (Bật/Tắt) nhanh chóng.
- Xóa các lịch hẹn không còn cần thiết.


## Công nghệ sử dụng (Tech Stack)
Dự án được xây dựng tuân thủ nghiêm ngặt các ràng buộc kỹ thuật của đề bài, tập trung vào nền tảng Android thuần túy:
- Ngôn ngữ lập trình: Java (Thuần túy, không sử dụng Kotlin).
- Giao diện người dùng (UI): XML Layouts (Không sử dụng Jetpack Compose).
- Cơ sở dữ liệu: `SQLite` với `SQLiteOpenHelper` (Thuần túy, không sử dụng Room Database, Firebase hay các REST API bên ngoài, cũng không dùng cấu trúc dữ liệu tĩnh tạm thời như `static ArrayList`).
- Giao tiếp thành phần: Chuyển đổi màn hình (Activity) bằng `Intent`, chỉ truyền dữ liệu định danh (`id`), đảm bảo tính nhất quán dữ liệu bằng cách truy vấn trực tiếp từ Database tại màn hình đích.

## Hướng dẫn cài đặt và Setup cho người dùng mới
### 1. Yêu cầu hệ thống
- Android Studio (khuyến nghị phiên bản mới nhất, ví dụ: Koala, Ladybug...).
- Java Development Kit (JDK) tương thích với phiên bản Android Studio (thường đi kèm sẵn).
- Thiết bị Android thật hoặc Android Emulator (API Level tối thiểu từ 24 trở lên khuyến nghị).

### 2. Các bước cài đặt
1. Tải mã nguồn:
   - Clone repository này về máy hoặc tải file ZIP mã nguồn và giải nén.
2. Mở dự án:
   - Khởi động Android Studio.
   - Chọn Open (hoặc File > Open).
   - Điều hướng đến thư mục chứa mã nguồn dự án (thư mục có chứa file `build.gradle` cấp root) và chọn OK.
3. Đồng bộ Gradle (Gradle Sync):
   - Chờ Android Studio tự động tải về các thư viện cần thiết và cấu hình dự án (Quá trình này yêu cầu kết nối Internet).
   - Nếu có thông báo yêu cầu "Sync Project with Gradle Files", hãy nhấp vào đó.
4. Chạy ứng dụng:
   - Chọn một máy ảo (Emulator) hoặc kết nối thiết bị thật (đã bật USB Debugging) trên thanh công cụ của Android Studio.
   - Nhấn nút Run 'app' (biểu tượng hình tam giác màu xanh lá cây) hoặc dùng phím tắt `Shift + F10`.
   - Android Studio sẽ biên dịch và tự động cài đặt ứng dụng lên thiết bị.

### 3. Thông tin Đăng nhập Mặc định
Khi chạy ứng dụng lần đầu tiên, hệ thống sẽ tự động tạo cơ sở dữ liệu với một số dữ liệu mẫu. Vui lòng sử dụng thông tin sau để đăng nhập:
- Tên đăng nhập: `admin`
- Mật khẩu: `123456`
