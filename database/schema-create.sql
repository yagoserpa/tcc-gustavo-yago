
CREATE EXTENSION pgcrypto;

CREATE SEQUENCE public.project_project_id_seq;

CREATE TABLE public.Project (
                project_id INTEGER NOT NULL DEFAULT nextval('public.project_project_id_seq'),
                name VARCHAR(255) NOT NULL,
                subject VARCHAR(100) NOT NULL,
                title VARCHAR(255),
                description VARCHAR(4000),
                status INTEGER,
                register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                keywords VARCHAR(250),
                CONSTRAINT project_pk PRIMARY KEY (project_id)
);


ALTER SEQUENCE public.project_project_id_seq OWNED BY public.Project.project_id;

CREATE SEQUENCE public.file_file_id_seq;

CREATE TABLE public.File (
                file_id INTEGER NOT NULL DEFAULT nextval('public.file_file_id_seq'),
                project_id INTEGER NOT NULL,
                name VARCHAR(45) NOT NULL,
                version INTEGER NOT NULL,
                file VARCHAR(4000) NOT NULL,
                register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                content_type VARCHAR(45),
                content VARCHAR(100),
                comments BYTEA,
                CONSTRAINT file_pk PRIMARY KEY (file_id)
);


ALTER SEQUENCE public.file_file_id_seq OWNED BY public.File.file_id;

CREATE SEQUENCE public.users_user_id_seq;

CREATE TABLE public.Users (
                user_id INTEGER NOT NULL DEFAULT nextval('public.users_user_id_seq'),
                name VARCHAR(45) NOT NULL,
                email VARCHAR(45) NOT NULL,
                password VARCHAR(255) NOT NULL,
                user_type SMALLINT NOT NULL,
                dre VARCHAR(11),
                siape VARCHAR(11),
                register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                gender VARCHAR(45),
                status SMALLINT,
                title VARCHAR(255),
                position VARCHAR(255),
                room VARCHAR(255),
                lattes VARCHAR(255),
                user_profile VARCHAR(255),
                course VARCHAR(255),
                origin VARCHAR(100) DEFAULT Universidade Federal do Rio de Janeiro NOT NULL,
                CONSTRAINT users_pk PRIMARY KEY (user_id)
);
COMMENT ON COLUMN public.Users.dre IS 'UNIQUE';
COMMENT ON COLUMN public.Users.siape IS 'UNIQUE';


ALTER SEQUENCE public.users_user_id_seq OWNED BY public.Users.user_id;

CREATE TABLE public.Project_has_User (
                project_id INTEGER NOT NULL,
                user_id INTEGER NOT NULL,
                committee BOOLEAN DEFAULT FALSE NOT NULL,
                coop BOOLEAN DEFAULT FALSE NOT NULL,
                CONSTRAINT project_has_user_pk PRIMARY KEY (project_id, user_id)
);


CREATE SEQUENCE public.fieldofinterest_field_id_seq;

CREATE TABLE public.FieldOfInterest (
                field_id INTEGER NOT NULL DEFAULT nextval('public.fieldofinterest_field_id_seq'),
                name VARCHAR(45) NOT NULL,
                description VARCHAR(4000),
                CONSTRAINT field_of_interest_pk PRIMARY KEY (field_id)
);


ALTER SEQUENCE public.fieldofinterest_field_id_seq OWNED BY public.FieldOfInterest.field_id;

CREATE TABLE public.FieldOfInterest_has_User (
                field_id INTEGER NOT NULL,
                user_id INTEGER NOT NULL,
                CONSTRAINT field_of_interest_has_user_pk PRIMARY KEY (field_id, user_id)
);


CREATE SEQUENCE public.record_record_id_seq;

CREATE TABLE public.Record (
                record_id INTEGER NOT NULL DEFAULT nextval('public.record_record_id_seq'),
                project_id INTEGER NOT NULL,
                thesis_id INTEGER NOT NULL,
                thesis_date DATE NOT NULL,
                begin_time TIME NOT NULL,
                end_time TIME NOT NULL,
                location VARCHAR(255) NOT NULL,
                evaluation INTEGER NOT NULL,
                deadline INTEGER,
                grade NUMERIC NOT NULL,
                CONSTRAINT record_pk PRIMARY KEY (record_id)
);


ALTER SEQUENCE public.record_record_id_seq OWNED BY public.Record.record_id;

CREATE TABLE public.Record_has_User (
                record_id INTEGER NOT NULL,
                project_id INTEGER NOT NULL,
                user_id INTEGER NOT NULL,
                signature BYTEA NOT NULL,
                CONSTRAINT record_has_user_pk PRIMARY KEY (record_id, project_id, user_id)
);


ALTER TABLE public.Project_has_User ADD CONSTRAINT projeto_projeto_has_professor_fk
FOREIGN KEY (project_id)
REFERENCES public.Project (project_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.File ADD CONSTRAINT projeto_arquivo_fk
FOREIGN KEY (project_id)
REFERENCES public.Project (project_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Record ADD CONSTRAINT project_record_fk
FOREIGN KEY (project_id)
REFERENCES public.Project (project_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FieldOfInterest_has_User ADD CONSTRAINT usuario_areadeinteresse_has_professor_fk
FOREIGN KEY (user_id)
REFERENCES public.Users (user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Project_has_User ADD CONSTRAINT usuario_projeto_has_professor_fk
FOREIGN KEY (user_id)
REFERENCES public.Users (user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Record_has_User ADD CONSTRAINT project_has_user_record_has_user_fk
FOREIGN KEY (project_id, user_id)
REFERENCES public.Project_has_User (project_id, user_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.FieldOfInterest_has_User ADD CONSTRAINT areadeinteresse_areadeinteresse_has_professor_fk
FOREIGN KEY (field_id)
REFERENCES public.FieldOfInterest (field_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Record_has_User ADD CONSTRAINT record_record_has_user_fk
FOREIGN KEY (record_id)
REFERENCES public.Record (record_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;