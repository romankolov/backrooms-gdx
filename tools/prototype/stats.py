import sys, collections
sys.path.insert(0,'/tmp/gen3')
from mirror import *
R=70; N=2*R+1
rooms=collections.Counter(); areas={}; holes=0; walls=0; cells=0
kinds=collections.Counter()
for cz in range(-R,R):
    for cx in range(-R,R):
        q=Q(); leaf_at(cx,cz,q)
        w,h=q.leaf[2]-q.leaf[0], q.leaf[3]-q.leaf[1]
        keys=(q.leaf[0],q.leaf[1],q.leaf[2],q.leaf[3])
        if keys not in areas: areas[keys]=(w,h); kinds[q.kind]+=1
        cells+=1
        if wall(1,cx+1,cz,q) or wall(0,cx,cz+1,q) or wall(1,cx,cz,q) or wall(0,cx,cz,q): walls+=1
        if is_hole(cx,cz,q): holes+=1
print("cells",cells,"wall ratio",round(walls/cells,4),"hole ratio",round(holes/cells,4))
print("rooms",len(areas),"mean area",round(sum(w*h for w,h in areas.values())/len(areas),1))
print("kinds",{k:v for k,v in sorted(kinds.items())})
# BFS
start=None
for cz in range(-R,R):
    for cx in range(-R,R):
        q=Q(); leaf_at(cx,cz,q)
        if not (wall(1,cx,cz,q) or wall(0,cx,cz,q) or is_hole(cx,cz,q)):
            start=(cx,cz); break
    if start: break
seen={start}; dq=collections.deque([start])
while dq:
    cx,cz=dq.popleft(); q=Q(); leaf_at(cx,cz,q)
    for dx,dz in ((1,0),(-1,0),(0,1),(0,-1)):
        nx,nz=cx+dx,cz+dz
        if not(-R<=nx<R and -R<=nz<R): continue
        if (nx,nz) in seen: continue
        q2=Q(); leaf_at(nx,nz,q2)
        blocked = wall(1, max(cx,nx), cz, q) or wall(0, cx, max(cz,nz), q) or is_hole(nx,nz,q2)
        if blocked: continue
        seen.add((nx,nz)); dq.append((nx,nz))
print("start",start,"reachable",round(100*len(seen)/cells,2),"%")
rooms_seen=set()
for cx,cz in seen:
    q=Q(); leaf_at(cx,cz,q); rooms_seen.add(q.leaf)
print("unreachable rooms", len(areas)-len(rooms_seen), "of", len(areas))
