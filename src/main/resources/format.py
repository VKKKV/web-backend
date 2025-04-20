import json

# 从文件中读取 JSON 数据
with open('stock_codes.conf', 'r', encoding='utf-8') as file:
    json_data = file.read()

# 解析 JSON 数据
data = json.loads(json_data)

# 删除每个字符串开头的一个零
data['stock'] = [s[1:] if s.startswith('0') else s for s in data['stock']]

# 将修改后的数据写回文件，去除换行
with open('stock_codes.conf', 'w', encoding='utf-8') as file:
    json.dump(data, file, ensure_ascii=False)  # 不设置 indent 参数，生成单行 JSON

# 输出结果（可选）
print("Updated stock codes:", data['stock'])


