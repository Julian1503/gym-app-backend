CREATE TABLE IF NOT EXISTS person (
                        person_id SERIAL PRIMARY KEY,
                        "name" VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        identifier CHAR(9),
                        identifier_type IdentifierType NOT NULL,
                        phone_number VARCHAR(15),
                        finger_print_data BYTEA,
                        photo BYTEA,
                        street VARCHAR(50),
                        house_number VARCHAR(10),
                        floor VARCHAR(4),
                        door VARCHAR(2),
                        gender GenderType NOT NULL,
                        birth_date DATE NOT NULL,
    CONSTRAINT unique_identifier UNIQUE (identifier)
);

CREATE TABLE IF NOT EXISTS exercise (exercise_id SERIAL PRIMARY KEY,
                                     "name" VARCHAR(150) NOT NULL,
    description VARCHAR(500) NOT NULL,
    muscle_group MuscleGroupType NOT NULL,
    difficulty_level SMALLINT NOT NULL,
    photo BYTEA);





CREATE TABLE IF NOT EXISTS specialty (
                           specialty_id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           description VARCHAR(200),
                           photo BYTEA
);

CREATE TABLE IF NOT EXISTS discount (
                                        discount_id SERIAL PRIMARY KEY ,
                                        percentage DECIMAL(5,2) NOT NULL,
    code VARCHAR(100) NOT NULL,
    expired BOOLEAN NOT NULL DEFAULT FALSE
    );


CREATE TABLE IF NOT EXISTS membership (membership_id SERIAL PRIMARY KEY,
                                       "name" VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    days SMALLINT);



CREATE TABLE IF NOT EXISTS trainer (
                         trainer_id SERIAL PRIMARY KEY,
                         trainer_number VARCHAR(20),
                         date_hired DATE,
                         active BOOLEAN DEFAULT true,
                        person_id INTEGER NOT NULL,
                         FOREIGN KEY (person_id) REFERENCES person (person_id),
    CONSTRAINT trainer_person_id_unique UNIQUE (person_id)
);

CREATE TABLE IF NOT EXISTS member (
                                      member_id SERIAL PRIMARY KEY,
                                      member_number VARCHAR(20) NOT NULL UNIQUE,
    join_date DATE NOT NULL,
    emergency_contact_name VARCHAR(70),
    emergency_contact_phone VARCHAR(15),
    person_id INTEGER NOT NULL,
    FOREIGN KEY (person_id) REFERENCES person (person_id),
    CONSTRAINT member_person_id_unique UNIQUE (person_id)
);

CREATE TABLE IF NOT EXISTS plan (
                                    plan_id SERIAL PRIMARY KEY,
                                    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    photo BYTEA,
    member_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member (person_id)
    );

CREATE TABLE IF NOT EXISTS day_plan (day_plan_id SERIAL PRIMARY KEY,
                                     day_name DayOfWeek NOT NULL,
                                     finished BOOLEAN NOT NULL DEFAULT FALSE,
                                     plan_id INTEGER NOT NULL,
                                     FOREIGN KEY (plan_id) REFERENCES PLAN (plan_id));

CREATE TABLE IF NOT EXISTS exercise_day_plan (
                                   exercises_day_plan_id SERIAL PRIMARY KEY,
                                   "order" SMALLINT NOT NULL,
                                   duration TIME NOT NULL,
                                   repetitions SMALLINT NOT NULL,
                                   series SMALLINT NOT NULL,
                                   warmup BOOLEAN NOT NULL DEFAULT FALSE,
                                   finished BOOLEAN NOT NULL DEFAULT FALSE,
                                   weight NUMERIC,
                                   exercise_id BIGINT NOT NULL,
                                   day_plan_id BIGINT NOT NULL,
                                   FOREIGN KEY (exercise_id) REFERENCES exercise (exercise_id),
                                   FOREIGN KEY (day_plan_id) REFERENCES day_plan (day_plan_id)
);


CREATE TABLE IF NOT EXISTS equipment (equipment_id SERIAL PRIMARY KEY,
                                        "name" VARCHAR(80) NOT NULL,
                                        "type" EquipmentType NOT NULL,
                                        quantity SMALLINT, exercise_id INTEGER NOT NULL,
                                        FOREIGN KEY (exercise_id) REFERENCES exercise (exercise_id));


CREATE TABLE IF NOT EXISTS exercise_specialty (trainer_specialty_id SERIAL PRIMARY KEY,
                                                exercise_id INTEGER NOT NULL,
                                                specialty_id INTEGER NOT NULL,
                                                FOREIGN KEY (exercise_id) REFERENCES exercise (exercise_id),
                                                FOREIGN KEY (specialty_id) REFERENCES specialty (specialty_id));

CREATE TABLE IF NOT EXISTS membership_subscription (membership_subscription_id SERIAL PRIMARY KEY,
                                                    start_date DATE NOT NULL,
                                                    end_date DATE NOT NULL,
                                                    amount NUMERIC(8, 2) NOT NULL,
                                                    discount_id INTEGER, membership_id INTEGER NOT NULL,
                                                    member_number VARCHAR(20) NOT NULL,
                                                    FOREIGN KEY (discount_id) REFERENCES discount (discount_id),
                                                    FOREIGN KEY (membership_id) REFERENCES membership (membership_id),
                                                    FOREIGN KEY (member_number) REFERENCES member (member_number));


CREATE TABLE IF NOT EXISTS step (step_id SERIAL PRIMARY KEY,
                                    description VARCHAR(500) NOT NULL,
                                    exercise_id INTEGER NOT NULL,
                                    FOREIGN KEY (exercise_id) REFERENCES exercise (exercise_id));
CREATE TABLE IF NOT EXISTS trainer_specialty (
                                   trainer_specialty_id SERIAL PRIMARY KEY,
                                   trainer_id BIGINT NOT NULL REFERENCES trainer(person_id),
                                   specialty_id BIGINT NOT NULL REFERENCES specialty(specialty_id)
);

CREATE TABLE IF NOT EXISTS trainer_plan (
                              trainer_id bigint NOT NULL,
                              plan_id bigint NOT NULL,
                              PRIMARY KEY (trainer_id, plan_id),
                              CONSTRAINT fk_trainer
                                  FOREIGN KEY (trainer_id)
                                      REFERENCES trainer(person_id),
                              CONSTRAINT fk_plan
                                  FOREIGN KEY (plan_id)
                                      REFERENCES plan(plan_id)
);