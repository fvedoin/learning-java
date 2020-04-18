public class clienteDao {
public static void inserecliente(cliente objeto){
String sql = 'INSERT INTO cliente(id,nome) VALUES(?,?)';
Connection conn = null;
PreparedStatement pstm = null;
try {
conn = Conexao.getConnection();
psmt = conn.prepareStatement(sql);
pmst.setString(1,objeto.getid());
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
public static void editacliente(id,nome){
String sql = "UPDATE cliente SET id = ?, nome = ?" + " WHERE id = ?";
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
public static void removecliente(id,nome){
String sql = 'DELETE FROM cliente WHERE id=1';
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
public static List<cliente> buscacliente(){
 String sql = "SELECT * FROM cliente";
List<cliente> results = new ArrayList<cliente>();
Connection conn = null;
PreparedStatement pstm = null;
ResultSet rset = null;
 try {
conn = Conexao.getConnection();
pstm = conn.prepareStatement(sql);
rset = pstm.executeQuery();
while(rset.next()){
cliente result = new cliente();
result.setid(rset.getString("id"))
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
