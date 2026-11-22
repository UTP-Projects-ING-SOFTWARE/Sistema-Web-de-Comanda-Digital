CREATE USER swcd_user WITH PASSWORD 'swcd_pass';
CREATE DATABASE swcd_db OWNER swcd_user;
GRANT ALL PRIVILEGES ON DATABASE swcd_db TO swcd_user;
