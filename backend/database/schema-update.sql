
### Migration 1 ###

ALTER TABLE ONLY project ALTER COLUMN "status" TYPE SMALLINT, ALTER COLUMN "status" SET NOT NULL;

ALTER TABLE ONLY record ALTER COLUMN "evaluation" TYPE SMALLINT, ALTER COLUMN "evaluation" SET NOT NULL;

alter table project drop column "name";

alter table file drop column "content_type";
