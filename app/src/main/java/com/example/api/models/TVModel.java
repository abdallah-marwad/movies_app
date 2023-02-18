package com.example.api.models;

import java.util.List;

public class TVModel {

    /**
     * page : 1
     * results : [{"backdrop_path":"/zaulpwl355dlKkvtAiSBE5LaoWA.jpg","first_air_date":"2010-10-31",
     * "genre_ids":[10759,18,10765],"id":1402,"name":"The Walking Dead",
     * "origin_country":["US"],"original_language":"en","original_name":"The Walking Dead","overview":"Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.","popularity":2685.774,"poster_path":"/xf9wuDcqlUPWABZNeDKPbZUjWx0.jpg","vote_average":8.1,"vote_count":14047},{"backdrop_path":"/5kkw5RT1OjTAMh3POhjo5LdaACZ.jpg","first_air_date":"2021-10-12","genre_ids":[80,10765],"id":90462,"name":"Chucky","origin_country":["US"],"original_language":"en","original_name":"Chucky","overview":"After a vintage Chucky doll turns up at a suburban yard sale, an idyllic American town is thrown into chaos as a series of horrifying murders begin to expose the town\u2019s hypocrisies and secrets. Meanwhile, the arrival of enemies \u2014 and allies \u2014 from Chucky\u2019s past threatens to expose the truth behind the killings, as well as the demon doll\u2019s untold origins.","popularity":2039.97,"poster_path":"/kY0BogCM8SkNJ0MNiHB3VTM86Tz.jpg","vote_average":7.9,"vote_count":3498},{"backdrop_path":"/3XjDhPzj7Myr8yzsTO8UB6E2oAu.jpg","first_air_date":"2011-02-28","genre_ids":[18,80],"id":31586,"name":"La Reina del Sur","origin_country":["US"],"original_language":"es","original_name":"La Reina del Sur","overview":"After years of blood, sweat and tears, a woman of humble origin ends up becoming a drug trafficking legend, with all that that means...","popularity":3081.88,"poster_path":"/uBTlJDdPpRxYTfUnKw4wbuIGSEK.jpg","vote_average":7.8,"vote_count":1420},{"backdrop_path":"/etj8E2o0Bud0HkONVQPjyCkIvpv.jpg","first_air_date":"2022-08-21","genre_ids":[10765,18,10759],"id":94997,"name":"House of the Dragon","origin_country":["US"],"original_language":"en","original_name":"House of the Dragon","overview":"The Targaryen dynasty is at the absolute apex of its power, with more than 15 dragons under their yoke. Most empires crumble from such heights. In the case of the Targaryens, their slow fall begins when King Viserys breaks with a century of tradition by naming his daughter Rhaenyra heir to the Iron Throne. But when Viserys later fathers a son, the court is shocked when Rhaenyra retains her status as his heir, and seeds of division sow friction across the realm.","popularity":1280.768,"poster_path":"/1X4h40fcB4WWUmIBK0auT4zRBAV.jpg","vote_average":8.5,"vote_count":2446},{"backdrop_path":"/cl8NLaoztP877hTSYSy6YIUkChF.jpg","first_air_date":"2022-09-26","genre_ids":[10764],"id":210855,"name":"Now what","origin_country":["BE"],"original_language":"nl","original_name":"Now what","overview":"7 young people co-house in Antwerp. They are all at the beginning of their adult life and have to decide what that should look like.","popularity":1369.347,"poster_path":"/89kiLK0S7Rbfjorvhm0vxTAgAH3.jpg","vote_average":4.3,"vote_count":3},{"backdrop_path":"/uGy4DCmM33I7l86W7iCskNkvmLD.jpg","first_air_date":"2013-12-02","genre_ids":[16,35,10765,10759],"id":60625,"name":"Rick and Morty","origin_country":["US"],"original_language":"en","original_name":"Rick and Morty","overview":"Rick is a mentally-unbalanced but scientifically gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.","popularity":1285.539,"poster_path":"/cvhNj9eoRBe5SxjCbQTkh05UP5K.jpg","vote_average":8.7,"vote_count":7404},{"backdrop_path":"/hIZFG7MK4leU4axRFKJWqrjhmxZ.jpg","first_air_date":"2022-10-20","genre_ids":[10765,18],"id":95403,"name":"The Peripheral","origin_country":["US"],"original_language":"en","original_name":"The Peripheral","overview":"Stuck in a small Appalachian town, a young woman\u2019s only escape from the daily grind is playing advanced video games. She is such a good player that a company sends her a new video game system to test\u2026but it has a surprise in store. It unlocks all of her dreams of finding a purpose, romance, and glamour in what seems like a game\u2026but it also puts her and her family in real danger.","popularity":1274.49,"poster_path":"/ccBe5BVeibdBEQU7l6P6BubajWV.jpg","vote_average":8.2,"vote_count":266},{"backdrop_path":"/o8zk3QmHYMSC7UiJgFk81OFF1sc.jpg","first_air_date":"2022-08-22","genre_ids":[10766,18],"id":204095,"name":"Mar do Sertão","origin_country":["BR"],"original_language":"pt","original_name":"Mar do Sertão","overview":"","popularity":1315.046,"poster_path":"/ixgnqO8xhFMb1zr8RRFsyeZ9CdD.jpg","vote_average":4.3,"vote_count":16},{"backdrop_path":"/6nQNLJEv4PgJlck3G4FIB4vJ99o.jpg","first_air_date":"2018-10-05","genre_ids":[80,9648,18],"id":76669,"name":"Elite","origin_country":["ES"],"original_language":"es","original_name":"Élite","overview":"When three working class kids enroll in the most exclusive school in Spain, the clash between the wealthy and the poor students leads to tragedy.","popularity":1710.129,"poster_path":"/3NTAbAiao4JLzFQw6YxP1YZppM8.jpg","vote_average":8.1,"vote_count":8457},{"backdrop_path":"/o5GsA1G5YEruuUNOYvWjlArIC37.jpg","first_air_date":"2022-09-19","genre_ids":[10766],"id":210506,"name":"Sangue Oculto","origin_country":["PT"],"original_language":"pt","original_name":"Sangue Oculto","overview":"","popularity":1277.865,"poster_path":"/myCEG6C5Nk181jXzBek5MQEXpM2.jpg","vote_average":2,"vote_count":1},{"backdrop_path":null,"first_air_date":"2022-09-19","genre_ids":[],"id":210941,"name":"Imlie y Cheeni","origin_country":["US"],"original_language":"en","original_name":"Imlie y Cheeni","overview":"This second story is about Imlie and Cheeni, daughters of Imlie and Aryan, Malini and Aditya.","popularity":1353.264,"poster_path":"/7nCLKRx18hzmjfOC0Zz6SBo8ZYO.jpg","vote_average":0,"vote_count":0},{"backdrop_path":"/1UCGE1Dl7iClKIbDMcGWiHKVWCU.jpg","first_air_date":"2022-05-30","genre_ids":[35,10759,10766],"id":197189,"name":"Cara e Coragem","origin_country":["BR"],"original_language":"pt","original_name":"Cara e Coragem","overview":"","popularity":1390.353,"poster_path":"/8CXbCCGiJxi4AXPBQ1QPrehMIGG.jpg","vote_average":5.6,"vote_count":26},{"backdrop_path":"/jANJGWIKavzyREXUMCHiXFzitDO.jpg","first_air_date":"2022-10-10","genre_ids":[10766,18],"id":204370,"name":"Travessia","origin_country":["BR"],"original_language":"pt","original_name":"Travessia","overview":"","popularity":1377.052,"poster_path":"/jFZJEoPzt2RKSsZG8QEWptX5Xyw.jpg","vote_average":4.8,"vote_count":5},{"backdrop_path":"/5DUMPBSnHOZsbBv81GFXZXvDpo6.jpg","first_air_date":"2022-10-12","genre_ids":[16,10759,10765,35],"id":114410,"name":"Chainsaw Man","origin_country":["JP"],"original_language":"ja","original_name":"チェンソーマン","overview":"Denji has a simple dream\u2014to live a happy and peaceful life, spending time with a girl he likes. This is a far cry from reality, however, as Denji is forced by the yakuza into killing devils in order to pay off his crushing debts. Using his pet devil Pochita as a weapon, he is ready to do anything for a bit of cash.","popularity":1157.679,"poster_path":"/yVtx7Xn9UxNJqvG2BkvhCcmed9S.jpg","vote_average":8.6,"vote_count":291},{"backdrop_path":"/5vUux2vNUTqwCzb7tVcH18XnsF.jpg","first_air_date":"2022-09-21","genre_ids":[80,18],"id":113988,"name":"Dahmer \u2013 Monster: The Jeffrey Dahmer Story","origin_country":["US"],"original_language":"en","original_name":"Dahmer \u2013 Monster: The Jeffrey Dahmer Story","overview":"Across more than a decade, 17 teen boys and young men were murdered by convicted killer Jeffrey Dahmer. How did he evade arrest for so long?","popularity":1087.832,"poster_path":"/f2PVrphK0u81ES256lw3oAZuF3x.jpg","vote_average":8.2,"vote_count":1589},{"backdrop_path":"/1rO4xoCo4Z5WubK0OwdVll3DPYo.jpg","first_air_date":"2022-09-01","genre_ids":[10765,10759,18],"id":84773,"name":"The Lord of the Rings: The Rings of Power","origin_country":["US"],"original_language":"en","original_name":"The Lord of the Rings: The Rings of Power","overview":"Beginning in a time of relative peace, we follow an ensemble cast of characters as they confront the re-emergence of evil to Middle-earth. From the darkest depths of the Misty Mountains, to the majestic forests of Lindon, to the breathtaking island kingdom of Númenor, to the furthest reaches of the map, these kingdoms and characters will carve out legacies that live on long after they are gone.","popularity":1075.694,"poster_path":"/mYLOqiStMxDK3fYZFirgrMt8z5d.jpg","vote_average":7.6,"vote_count":1537},{"backdrop_path":"/caGVr9Il2gj8bN4ow6qsLm60TxM.jpg","first_air_date":"2005-03-27","genre_ids":[18],"id":1416,"name":"Grey's Anatomy","origin_country":["US"],"original_language":"en","original_name":"Grey's Anatomy","overview":"Follows the personal and professional lives of a group of doctors at Seattle\u2019s Grey Sloan Memorial Hospital.","popularity":1046.253,"poster_path":"/daSFbrt8QCXV2hSwB0hqYjbj681.jpg","vote_average":8.3,"vote_count":8625},{"backdrop_path":"/2OMB0ynKlyIenMJWI2Dy9IWT4c.jpg","first_air_date":"2011-04-17","genre_ids":[10765,18,10759],"id":1399,"name":"Game of Thrones","origin_country":["US"],"original_language":"en","original_name":"Game of Thrones","overview":"Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.","popularity":878.887,"poster_path":"/7WUHnWGx5OO145IRxPDUkQSh4C7.jpg","vote_average":8.4,"vote_count":19791},{"backdrop_path":"/rv5gu2gYbOEYoArzH7bqJuMxvBB.jpg","first_air_date":"2021-01-25","genre_ids":[18,10751],"id":115646,"name":"Lisa","origin_country":["BE"],"original_language":"nl","original_name":"Lisa","overview":"","popularity":1198.956,"poster_path":"/w2nOl7KhwcUj11YxEi9Nknj9cqu.jpg","vote_average":6.5,"vote_count":30},{"backdrop_path":"/s1xnjbOIQtwGObPnydTebp74G2c.jpg","first_air_date":"2022-11-17","genre_ids":[9648,18],"id":90669,"name":"1899","origin_country":["DE"],"original_language":"en","original_name":"1899","overview":"Passengers on an immigrant ship traveling to the new continent get caught in a mysterious riddle when they find a second vessel adrift on the open sea.","popularity":1177.805,"poster_path":"/2QK8tIXafyiz93PvAbKxxfK2BLb.jpg","vote_average":7.9,"vote_count":163}]
     * total_pages : 7013
     * total_results : 140244
     */

    private int page;
    private int total_pages;
    private int total_results;
    private List<ResultsBean> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * backdrop_path : /zaulpwl355dlKkvtAiSBE5LaoWA.jpg
         * first_air_date : 2010-10-31
         * genre_ids : [10759,18,10765]
         * id : 1402
         * name : The Walking Dead
         * origin_country : ["US"]
         * original_language : en
         * original_name : The Walking Dead
         * overview : Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.
         * popularity : 2685.774
         * poster_path : /xf9wuDcqlUPWABZNeDKPbZUjWx0.jpg
         * vote_average : 8.1
         * vote_count : 14047
         */

        private String backdrop_path;
        private String first_air_date;
        private int id;
        private String name;
        private String original_language;
        private String original_name;
        private String overview;
        private double popularity;
        private String poster_path;
        private double vote_average;
        private int vote_count;
        private List<Integer> genre_ids;
        private List<String> origin_country;

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getFirst_air_date() {
            return first_air_date;
        }

        public void setFirst_air_date(String first_air_date) {
            this.first_air_date = first_air_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginal_name() {
            return original_name;
        }

        public void setOriginal_name(String original_name) {
            this.original_name = original_name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public List<String> getOrigin_country() {
            return origin_country;
        }

        public void setOrigin_country(List<String> origin_country) {
            this.origin_country = origin_country;
        }
        @Override
        public boolean equals(Object o) {

            return super.equals(o);
        }
    }
}
