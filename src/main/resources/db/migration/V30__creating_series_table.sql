CREATE TABLE series_register (
                                 series_register_id SERIAL PRIMARY KEY,
                                 repetitions SMALLINT NOT NULL,
                                 weight SMALLINT NOT NULL,
                                 rest SMALLINT NOT NULL,
                                 duration SMALLINT NOT NULL,
                                 exercises_day_plan_id BIGINT,
                                 is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                 series_order SMALLINT NOT NULL
);

ALTER TABLE series_register
    ADD CONSTRAINT fk_series_register_exercise_day_plan FOREIGN KEY (exercises_day_plan_id)
        REFERENCES exercise_day_plan (exercises_day_plan_id);

ALTER TABLE exercise_day_plan
DROP COLUMN duration,
  DROP COLUMN repetitions,
  DROP COLUMN series;