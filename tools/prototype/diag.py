import sys, collections
sys.path.insert(0,'/tmp/gen3')
import mirror as m
m.FEATURE_MARGIN = int(sys.argv[1]) if len(sys.argv)>1 else 1
m.SEED = 4242
R = 40
sealed = []
for i in range(-R,R):
    for j in range(-R,R):
        if m.is_hole(i,j,m.Q()): continue
        q=m.Q(); m.leaf_at(i,j,q); h=m.is_hole(i,j,q)
        vw=lambda x,z: m.wall(1,x,z,(lambda: (lambda qq: (m.leaf_at(x,z,qq), qq)[1])(m.Q()))())
        # wall() takes q but only needs the separating cut; use a fresh query per cell
        qa=m.Q(); m.leaf_at(i,j,qa); qb=m.Q(); m.leaf_at(i+1,j,qb); qc=m.Q(); m.leaf_at(i,j+1,qc)
        open_ = (not m.wall(1,i,j,qa)) or (not m.wall(1,i+1,j,qb)) or (not m.wall(0,i,j,qa)) or (not m.wall(0,i,j+1,qc))
        if not open_: sealed.append((i,j))
print("sealed cells (R=%d):"%R, len(sealed), sealed[:6])
