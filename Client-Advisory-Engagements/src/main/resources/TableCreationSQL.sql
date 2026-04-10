DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS advisory_service;
DROP TABLE IF EXISTS engagement;

CREATE TYPE client_tier_enum AS ENUM('Standard', 'Premium', 'Private Banking');
CREATE TYPE client_net_worth_enum AS ENUM ('UNDER_500K', 'BETWEEN_500K_2M', 'BETWEEN_2M_10M', 'OVER_10M');

CREATE TABLE IF NOT EXISTS client (
	client_id BIGSERIAL PRIMARY KEY,              /** auto increment is great for basic int, spring can generate UUID if desired */
	first_name VARCHAR(50) NOT NULL,                   
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phone VARCHAR(50) NOT NULL, /** making text in case of country codes, dash format, etc */
	tier client_tier_enum NOT NULL,
	net_worth client_net_worth_enum NOT NULL
	/** Making everything not null right now because it feels right, might need to change later */
	/** Note to self, use ALTER TABLE later on to adjust */
);

CREATE TYPE advisory_service_type_enum AS ENUM ('Budgeting', 'Cash Flow Analysis', 'Debt Management', 'Estate Planning', 
'Investment Management', 'Retirement Planning', 'Risk Management and Insurance', 'Tax Planning');
CREATE TYPE advisory_service_delivery_format_enum AS ENUM ('In-Person', 'Virtual', 'Hybrid');

CREATE TABLE IF NOT EXISTS advisory_service (
	advisory_service_id BIGSERIAL PRIMARY KEY,
	service_name VARCHAR(50) NOT NULL,
	service_type advisory_service_type_enum NOT NULL,
	delivery_format advisory_service_delivery_format_enum NOT NULL,
	is_active_status BOOLEAN NOT NULL DEFAULT TRUE, /** options are available / discontinued in write-up */
	annual_fee NUMERIC(10,2) /** copied from chinook-example's table creation for a price  */
);

CREATE TYPE engagement_status_enum AS ENUM ('Active', 'Paused', 'Completed');

CREATE TABLE IF NOT EXISTS engagement (
	engagement_id BIGSERIAL PRIMARY KEY,
	advisory_service_id INTEGER REFERENCES advisory_service(advisory_service_id),
	client_id INTEGER REFERENCES client(client_id),
	start_date DATE DEFAULT CURRENT_DATE,
	engagement_status engagement_status_enum NOT NULL,
	notes VARCHAR(300)
);
