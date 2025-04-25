import pandas as pd


def generate_insert_statements(csv_file, db, table, output_file):
    df = pd.read_csv(csv_file)

    with open(output_file, mode='w', encoding='utf-8') as file:
        statement = f"INSERT INTO {db}.{table} (product_id, category_id, unit, is_available, stock_quantity, name, description, price) VALUES\n"
        for _, row in df.iterrows():
            statement += f"(\"{row["product_id"]}\",1,1,1,1,\"{row["product_name"]}\",\"{row["product_name"]}\",{row["price"]}),\n"

        statement = statement.rstrip(",\n") + ";\n"
        file.write(statement)


csv_file_path = 'datasets/products.csv'
db = 'grocery'
table_name = 'products'
output_file_path = '../src/main/resources/db/changelog/data/products/insert.sql'

generate_insert_statements(csv_file_path, db, table_name, output_file_path)
