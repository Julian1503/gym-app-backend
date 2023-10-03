ALTER TABLE EQUIPMENT DROP COLUMN exercise_id;
CREATE SEQUENCE exercise_equipment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    CACHE 1;


CREATE TABLE exercise_equipment (
                                    exercises_equipment_id BIGINT DEFAULT nextval('exercise_equipment_id_seq') PRIMARY KEY,
                                    exercise_id BIGINT NOT NULL REFERENCES exercise (exercise_id),
                                    equipment_id BIGINT NOT NULL REFERENCES equipment (equipment_id)
);