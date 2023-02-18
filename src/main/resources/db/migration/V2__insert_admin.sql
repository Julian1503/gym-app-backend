INSERT INTO users (avatar, username, password, email, name, surname)
VALUES (null, 'admin', '526d766f6b64d30df803c0ec7b5b476653e16e73310bb12c94bc001cf3324213c0e3451505eabd8a10064099ac5777d2', 'admin@admin.com', 'Admin', 'User');

-- Insert configuration for user
INSERT INTO configurations (date_format, time_format, language, user_id)
VALUES (0, 0, 0, 1);