DO $$
BEGIN
    FOR i IN 1..1000 LOOP
        INSERT INTO posts (post_id, title, url, posted_by, score, current_votes, created_at, time_elapsed)
        VALUES (
            i,  -- Manually set post_id
            'Title ' || i,
            'https://example.com/' || i,
            'User ' || i,
            (RANDOM() * 100),  -- Random score between 0 and 100
            (FLOOR(RANDOM() * 50)),  -- Random votes between 0 and 50
            NOW() - (i || ' hours')::interval,  -- Created at different times
            i  -- Time elapsed
        );
    END LOOP;
END $$;
