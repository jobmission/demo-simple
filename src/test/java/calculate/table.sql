-- 原始数据点表(ptag)
CREATE TABLE ptag (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 计算变量表(vtag)
CREATE TABLE vtag (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    formula TEXT NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 依赖关系表(用于检测循环依赖)
CREATE TABLE vtag_dependency (
    vtag_id INTEGER REFERENCES vtag(id),
    depends_on_tag_id INTEGER REFERENCES vtag(id),
    PRIMARY KEY (vtag_id, depends_on_tag_id)
);


-- 原始数据值表
CREATE TABLE ptag_value (
    tag_id INTEGER NOT NULL,
    tag_time TIMESTAMP NOT NULL,
    tag_value DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (tag_id, tag_time)
);

-- 虚拟数据值表
CREATE TABLE vtag_value (
    tag_id INTEGER NOT NULL,
    tag_time TIMESTAMP NOT NULL,
    tag_value DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (tag_id, tag_time)
);

-- 创建索引
CREATE INDEX idx_vtag_dependency_vtag_id ON vtag_dependency(vtag_id);
CREATE INDEX idx_vtag_dependency_depends ON vtag_dependency(depends_on_tag_id);
