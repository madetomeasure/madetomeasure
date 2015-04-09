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

Copyright (C) 2015 Lorenzo Ciacci, Christopher Hobbs, Matthew Kirk, Sophia Kirk, Max Spransy

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact us via: boss@modulus7.com
