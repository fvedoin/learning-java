public class produtoDao {
public static void insereproduto(produto objeto){
String sql = 'INSERT INTO produto(codigo,nome) VALUES(?,?)';
Connection conn = null;
PreparedStatement pstm = null;
try {
conn = Conexao.getConnection();
psmt = conn.prepareStatement(sql);
pmst.setString(1,objeto.getcodigo());
pmst.setString(2,objeto.getnome());
psmt.execute();
}catch (Exception e) {
e.printStackTrace();
}finally{
try{
if(pstm != null){
pstm.close();
}
if(conn != null){
conn.close();
}
}catch(Exception e){
e.printStackTrace();
}
}
}
public static void editaproduto(codigo,nome){
String sql = "UPDATE produto SET codigo = ?, nome = ?" + " WHERE id = ?";
Connection conn = null;
PreparedStatement pstm = null;
try {
conn = Conexao.getConnection();
psmt = conn.prepareStatement(sql);
psmt.execute();
}catch (Exception e) {
e.printStackTrace();
}finally{
try{
if(pstm != null){
pstm.close();
}
if(conn != null){
conn.close();
}
}catch(Exception e){
e.printStackTrace();
}
}
}
public static void removeproduto(codigo,nome){
String sql = 'DELETE FROM produto WHERE id=1';
Connection conn = null;
PreparedStatement pstm = null;
try {
conn = Conexao.getConnection();
pstm = conn.prepareStatement(sql);
pstm.execute();
} catch (Exception e) {
e.printStackTrace();
}finally{
e.printStackTrace();
try{
if(pstm != null){
pstm.close();
}
if(conn != null){
conn.close();
}
}catch(Exception e){
e.printStackTrace();
}
}
}
}
public static List<produto> buscaproduto(){
 String sql = "SELECT * FROM produto";
List<produto> results = new ArrayList<produto>();
Connection conn = null;
PreparedStatement pstm = null;
ResultSet rset = null;
 try {
conn = Conexao.getConnection();
pstm = conn.prepareStatement(sql);
rset = pstm.executeQuery();
while(rset.next()){
produto result = new produto();
result.setcodigo(rset.getString("codigo"))
result.setnome(rset.getString("nome"))
results.add(result)
}
} catch (Exception e) {
e.printStackTrace();
}finally{
try{
if(rset != null){
rset.close();
}
if(pstm != null){
pstm.close();
}
if(conn != null){
conn.close();
}
}catch(Exception e){
e.printStackTrace();
}
}
return results;
}
}
