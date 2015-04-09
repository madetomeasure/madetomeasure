## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Migrations

We use ragtime to handle migrations and instead of active record style migrations it utilizes SQL.

The basic way they are written is: `./migrations/${date}_name_.up.sql` and `./migrations/${date}_name_.down.sql`

It's important to ragtime to have both an up and down migration. That is solved using the following commands though

### Making a new migration

To make a new migration run `script/new_migration NAME_OF_MIGRATION_NO_SPACES`

### Migrating

Migrating is simple as `lein ragtime migrate`

### Rollbacks

Rollbacks are simple as `lein ragtime rollback`

## Help

If you get lost type `lein help` Usually it'll have what you need

## License

The Made to Measure API is AGPL see License.txt

Copyright 2015 Made to Measure

