
### Migration 1 ###

ALTER TABLE ONLY project ALTER COLUMN "status" TYPE SMALLINT, ALTER COLUMN "status" SET NOT NULL;

ALTER TABLE ONLY record ALTER COLUMN "evaluation" TYPE SMALLINT, ALTER COLUMN "evaluation" SET NOT NULL;

alter table project drop column "name";

alter table file drop column "content_type";

ALTER TABLE ONLY "users" ALTER COLUMN "name" TYPE VARCHAR(45), ALTER COLUMN "name" DROP NOT NULL;

ALTER TABLE ONLY "users" ALTER COLUMN "password" TYPE VARCHAR(255), ALTER COLUMN "password" DROP NOT NULL;

ALTER TABLE ONLY "users" ALTER COLUMN "origin" TYPE VARCHAR(100), ALTER COLUMN "origin" DROP NOT NULL;

CREATE SEQUENCE "register_token_register_token_id_seq";

CREATE TABLE "Register_Token" (
                "register_token_id" INTEGER NOT NULL DEFAULT nextval('"register_token_register_token_id_seq"'),
                "user_id" INTEGER NOT NULL,
                "token" VARCHAR(40) NOT NULL,
                CONSTRAINT "register_token_pk" PRIMARY KEY ("register_token_id")
);


ALTER SEQUENCE "register_token_register_token_id_seq" OWNED BY "Register_Token"."register_token_id";

ALTER TABLE "Register_Token" ADD CONSTRAINT "users_register_token_fk"
FOREIGN KEY ("user_id")
REFERENCES "users" ("user_id")
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.users ALTER COLUMN origin DROP DEFAULT;

ALTER TABLE "Register_Token" RENAME TO register_token;

ALTER TABLE users ADD CONSTRAINT email_unique UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT dre_unique UNIQUE (dre);
ALTER TABLE users ADD CONSTRAINT siape_unique UNIQUE (siape);
